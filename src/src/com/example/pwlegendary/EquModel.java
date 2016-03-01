/*
 * 装备
 * */
package com.example.pwlegendary;
//www.javaapk.com
public class EquModel {

	//名称
	private String SysName;
	//攻击力
	private String SysSAttack;
	//生命
	private String SysHP;
	//防御
	private String SysDefA;
	//幸运
	private String SysLucky;
	
	public String getSysLucky() {
		return SysLucky;
	}
	public void setSysLucky(String sysLucky) {
		SysLucky = sysLucky;
	}
	public String getSysName() {
		return SysName;
	}
	public void setSysName(String sysName) {
		SysName = sysName;
	}
	public String getSysDefA() {
		return SysDefA;
	}
	public void setSysDefA(String sysDefA) {
		SysDefA = sysDefA;
	}
	public String getSysSAttack() {
		return SysSAttack;
	}
	public void setSysSAttack(String sysSAttack) {
		SysSAttack = sysSAttack;
	}
	public String getSysHP() {
		return SysHP;
	}
	public void setSysHP(String sysHP) {
		SysHP = sysHP;
	}
}
