/*
 * 角色状态信息
 * */
package com.example.pwlegendary;

public class RoleInfoModel {

	//角色ID
	private String SysID;
	//角色名称
	private String SysName;
	//角色性别
	private String SysSex;
	//角色类型
	private String SysType;
	//角色等级
	private String SysLev;
	//角色经验
	private String SysExp;
	
	public String getSysID() {
		return SysID;
	}
	public void setSysID(String sysID) {
		SysID = sysID;
	}
	public String getSysName() {
		return SysName;
	}
	public void setSysName(String sysName) {
		SysName = sysName;
	}
	public String getSysSex() {
		return SysSex;
	}
	public void setSysSex(String sysSex) {
		SysSex = sysSex;
	}
	public String getSysType() {
		return SysType;
	}
	public void setSysType(String sysType) {
		SysType = sysType;
	}
	public String getSysLev() {
		return SysLev;
	}
	public void setSysLev(String sysLev) {
		SysLev = sysLev;
	}
	public String getSysExp() {
		return SysExp;
	}
	public void setSysExp(String sysExp) {
		SysExp = sysExp;
	}
	
	
}
