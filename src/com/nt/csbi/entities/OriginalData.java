package com.nt.csbi.entities;

import java.sql.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年4月30日 上午10:34:03
 */
public class OriginalData {

	private Integer originalDataId;
	private String originalDataName;
	private String titleInfo;
	private String tableOne;
	private String tableTwo;
	private String tableThree;
	private String tableFour;
	private String tableFive;
	private String tableSix;
	private String tableSeven;
	private String tableEight;
	private String autograph;
	private String showOptions;

	private Timestamp login;
	private Timestamp logout;

	public Integer getOriginalDataId() {
		return originalDataId;
	}

	public void setOriginalDataId(Integer originalDataId) {
		this.originalDataId = originalDataId;
	}

	public String getTitleInfo() {
		return titleInfo;
	}

	public void setTitleInfo(String titleInfo) {
		this.titleInfo = titleInfo;
	}

	public String getTableOne() {
		return tableOne;
	}

	public void setTableOne(String tableOne) {
		this.tableOne = tableOne;
	}

	public String getTableTwo() {
		return tableTwo;
	}

	public void setTableTwo(String tableTwo) {
		this.tableTwo = tableTwo;
	}

	public String getTableThree() {
		return tableThree;
	}

	public void setTableThree(String tableThree) {
		this.tableThree = tableThree;
	}

	public String getTableFour() {
		return tableFour;
	}

	public void setTableFour(String tableFour) {
		this.tableFour = tableFour;
	}

	public String getTableFive() {
		return tableFive;
	}

	public void setTableFive(String tableFive) {
		this.tableFive = tableFive;
	}

	public String getTableSix() {
		return tableSix;
	}

	public void setTableSix(String tableSix) {
		this.tableSix = tableSix;
	}

	public String getTableSeven() {
		return tableSeven;
	}

	public void setTableSeven(String tableSeven) {
		this.tableSeven = tableSeven;
	}

	public String getTableEight() {
		return tableEight;
	}

	public void setTableEight(String tableEight) {
		this.tableEight = tableEight;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getOriginalDataName() {
		return originalDataName;
	}

	public void setOriginalDataName(String originalDataName) {
		this.originalDataName = originalDataName;
	}

	public Timestamp getLogin() {
		return login;
	}

	public void setLogin(Timestamp login) {
		this.login = login;
	}

	public Timestamp getLogout() {
		return logout;
	}

	public void setLogout(Timestamp logout) {
		this.logout = logout;
	}

	public String getShowOptions() {
		return showOptions;
	}

	public void setShowOptions(String showOptions) {
		this.showOptions = showOptions;
	}
	
}
