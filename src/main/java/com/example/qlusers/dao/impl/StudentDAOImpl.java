package com.example.qlusers.dao.impl;

import com.example.qlusers.dao.StudentDAO;
import com.example.qlusers.entity.Students;
import com.example.qlusers.utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO
{
	
	@Override
	public List<Students> findAll()
	{
		//1. Tạo kết nối
		Connection con = ConnectionDB.openConnection();
		List<Students> students = new ArrayList<>();
		
		try
		{
			//2. Thực thi truy vấn
//            PreparedStatement ps = con.prepareStatement("select * from Students");
			CallableStatement call = con.prepareCall("{call PROC_GET_ALL_USER()}");
			ResultSet rs = call.executeQuery();
			while (rs.next())
			{
				Students s = new Students();
				s.setId(rs.getInt("id"));
				s.setFullName(rs.getString("fullName"));
				s.setEmail(rs.getString("email"));
				s.setAddress(rs.getString("address"));
				s.setPhone(rs.getString("phone"));
				s.setStatus(rs.getBoolean("status"));
				students.add(s);
			}
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			// 3. Đóng kết nối
			ConnectionDB.closeConnection(con);
		}
		return students;
	}
	
	@Override
	public Students findById(Integer id)
	{
		//1. Mở kết nối đến database
		Connection con = ConnectionDB.openConnection();
		try
		{
//			PreparedStatement ps = con.prepareStatement("select * from students where id = ?");
			CallableStatement call = con.prepareCall("{call PROC_GET_BY_ID(?)}");
			call.setInt(1, id);
			ResultSet rs = call.executeQuery();
			if (rs.next())
			{
				Students s = new Students();
				s.setId(rs.getInt("id"));
				s.setFullName(rs.getString("fullName"));
				s.setEmail(rs.getString("email"));
				s.setAddress(rs.getString("address"));
				s.setPhone(rs.getString("phone"));
				s.setStatus(rs.getBoolean("status"));
				return s;
			}
			else
			{
				return null;
			}
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			//3. Đóng kết nối
			ConnectionDB.closeConnection(con);
		}
	}
	
	@Override
	public boolean addStudent(Students student)
	{
		//1. Mở kết nối đến database
		Connection con = ConnectionDB.openConnection();
		
		try
		{
//			PreparedStatement ps = con.prepareStatement("insert into students (fullname, email, address, phone, status) values (?, ?, ? ,?, ?)");
			CallableStatement call = con.prepareCall("{call PROC_ADD_STUDENT(?,?,?,?,?)}");
			call.setString(1, student.getFullName());
			call.setString(2, student.getEmail());
			call.setString(3, student.getAddress());
			call.setString(4, student.getPhone());
			call.setBoolean(5, student.isStatus());
			call.executeUpdate();
			return true;
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			//3. Đóng kết nối
			ConnectionDB.closeConnection(con);
		}
		
	}
	
	@Override
	public boolean updateStudent(Students student)
	{
		//1. Mở kết nối đến database
		Connection con = ConnectionDB.openConnection();
		
		try
		{
//			PreparedStatement ps = con.prepareStatement("update students set fullname = ?, email = ?, address = ?, phone = ?, status = ? where id = ?");
			CallableStatement call = con.prepareCall("{call PROC_UPDATE_STUDENT(?,?,?,?,?,?)}");
			call.setString(1, student.getFullName());
			call.setString(2, student.getEmail());
			call.setString(3, student.getAddress());
			call.setString(4, student.getPhone());
			call.setBoolean(5, student.isStatus());
			call.setInt(6, student.getId());
			call.executeUpdate();
			return true;
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			//3. Đóng kết nối
			ConnectionDB.closeConnection(con);
		}
		
	}
	
	@Override
	public boolean deleteStudent(Integer id)
	{
		//1. Mở kết nối đến database
		Connection con = ConnectionDB.openConnection();
		
		try
		{
//			PreparedStatement ps = con.prepareStatement("delete from students where id = ?");
			CallableStatement call = con.prepareCall("{call PROC_DELETE_STUDENT(?)}");
			call.setInt(1, id);
			call.executeUpdate();
			return true;
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			//3. Đóng kết nối
			ConnectionDB.closeConnection(con);
		}
	}
	
	@Override
	public List<Students> findByName(String studentName)
	{
		List<Students> students = new ArrayList<>();
		//1. Mở kết nối đến database
		Connection con = ConnectionDB.openConnection();
		
		try
		{
		
//			PreparedStatement ps = con.prepareStatement("select * from students where fullname like ?");
			CallableStatement call = con.prepareCall("{call PROC_SEARCH_BY_NAME(?)}");
			call.setString(1,studentName);
			ResultSet rs = call.executeQuery();
			while (rs.next())
			{
				Students s = new Students();
				s.setId(rs.getInt("id"));
				s.setFullName(rs.getString("fullName"));
				s.setEmail(rs.getString("email"));
				s.setAddress(rs.getString("address"));
				s.setPhone(rs.getString("phone"));
				s.setStatus(rs.getBoolean("status"));
				students.add(s);
			}
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			//3. Đóng kết nối
			ConnectionDB.closeConnection(con);
		}
		return students;
	}
}
