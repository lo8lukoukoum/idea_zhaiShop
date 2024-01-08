package me.ilt.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ilt.Bean.addressBean;
import me.ilt.Bean.userBean;
import me.ilt.Dao.addressDao;
import me.ilt.Dao.usersDao;
import me.ilt.Util.responseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class addressServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("�յ�����");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String MethodName = request.getServletPath();
		MethodName = MethodName.substring(1, MethodName.length() - 8);
		System.out.println("MethodName��"+MethodName);
		try {
			Method method = getClass().getDeclaredMethod(MethodName,
					HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	/**
	 * ����Ĭ�ϵ�ַ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void setDefault(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int addressId = Integer.parseInt(request.getParameter("id"));
		String username = (String)request.getSession().getAttribute("username");
		if(username == null){
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		int userId = usersDao.nameIsId(username);
		addressDao.xgzt(userId);
		int i = addressDao.setDefeat(userId, addressId);
		JSONObject json = new JSONObject();
		if(i!=0){
			json.put("success", true);
		}else{
			json.put("error", true);
		}
		responseUtil.write(response, json);
		
	}
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int addressId = Integer.parseInt(request.getParameter("id"));
		int i = addressDao.del(addressId);
		JSONObject json = new JSONObject();
		if(i!=0){
			json.put("success", true);
		}else{
			json.put("error", true);
		}
		responseUtil.write(response, json);
	}
	/**
	 * ���ӵ�ַ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("�����˵�ַ����");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		String address = request.getParameter("posi");
		String phone = request.getParameter("pho");
		String username = request.getParameter("rel"); //��д���ջ���
		String msg = request.getParameter("msg"); //״̬
		
		String uname = request.getParameter("userName");
		if(uname==null){
			uname = (String)request.getSession().getAttribute("username");
			if(uname==null){
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
		System.out.println("��ȡ������Ϊ��"+uname);
		int userId = usersDao.nameIsId(uname); //�����û��ǳƻ�ȡ�û�ID
		if(msg.equals("1")){
			//����userId����  ȥ��������Ĭ��ֵ
			addressDao.xgzt(userId);
		}
		
		System.out.println("�����û����ֲ�ID��"+userId);
		
		addressBean bean = new addressBean(province, city, area, address, phone, username, Integer.parseInt(msg), userId);
		int id = addressDao.add(bean);
		JSONObject result=new JSONObject();
		System.out.println("���ص�ID�ǣ�"+id);
		
		if(id!=0){
			result.put("success", "true");
			result.put("AddressId", id);
		}else{
			result.put("errorMsg", "����ʧ��");
		}
		responseUtil.write(response, result);
	}
	
}