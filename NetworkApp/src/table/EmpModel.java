package table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EmpModel extends AbstractTableModel{
	List<Emp> list = new ArrayList<Emp>();
	String[] column = {"EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO"};
	TableMain tableMain;
	
	public EmpModel(TableMain tableMain) {
		this.tableMain = tableMain;
	}
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return column.length;
	}
	public String getColumnName(int col) {
		return column[col];
	}
	
	public Object getValueAt(int row, int col) {
//	모델이 갖고있는 값을 가져오는
		Emp emp = list.get(row);
		
		String value = "";
		switch(col) {
		case 0 : value = Integer.toString(emp.getEmpno()); break;
		case 1 : value = emp.getEname(); break;
		case 2 : value = emp.getJob(); break;
		case 3 : value = Integer.toString(emp.getMgr()); break;
		case 4 : value = emp.getHiredate(); break;
		case 5 : value = Integer.toString(emp.getSal()); break;
		case 6 : value = Integer.toString(emp.getComm()); break;
		case 7 : value = Integer.toString(emp.getDeptno()); break;
		}
		return value;
	}
//	테이블을 구성하고 있는 각 셀을 편집할 수 있는지 여부
	public boolean isCellEditable(int row, int col) {
		boolean flag = false;
		
		switch(col) {
		case 1 : flag = true; break;
		case 2 : flag = true; break;
		case 4 : flag = true; break;
		case 5 : flag = true; break;
		case 6 : flag = true; break;
		}
		return flag;
	}

	public void setValueAt(Object value, int row, int col) {
//	모델이 갖고있는 값을 수정하는
		System.out.println(row+", "+col+"의 값을"+value+" 로 변경할게요");
		
		Emp emp = list.get(row);
		
		switch(col) {
		case 1 : emp.setEname((String)value);; break; // 이름 변경
		case 2 : emp.setJob((String)value);; break; // 업무 변경
		case 4 : emp.setHiredate((String)value);; break; // 입사일 	변경
		case 5 : emp.setSal(Integer.parseInt((String)value)); break; // 급여 변경
		case 6 : emp.setComm(Integer.parseInt((String)value));; break; // 커미션 변경
		}
//		스위치 문 밑에서 이미 DTO 변경이 완료되었으므로 이 시점에 DB update 
		tableMain.dao.update(emp);

	}

}
