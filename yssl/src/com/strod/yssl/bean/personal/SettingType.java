package com.strod.yssl.bean.personal;

public class SettingType {

	private int leftIcon;
	private int leftText;
	private boolean showRightBtn;
	private String rightText;
	public SettingType() {
		super();
	}
	public SettingType(int leftText, boolean showRightBtn, String rightText) {
		super();
		this.leftText = leftText;
		this.showRightBtn = showRightBtn;
		this.rightText = rightText;
	}

	public SettingType(int leftIcon, int leftText, boolean showRightBtn, String rightText) {
		super();
		this.leftIcon = leftIcon;
		this.leftText = leftText;
		this.showRightBtn = showRightBtn;
		this.rightText = rightText;
	}

	public int getLeftIcon() {
		return leftIcon;
	}

	public void setLeftIcon(int leftIcon) {
		this.leftIcon = leftIcon;
	}

	public int getLeftText() {
		return leftText;
	}
	public void setLeftText(int leftText) {
		this.leftText = leftText;
	}
	public boolean isShowRightBtn() {
		return showRightBtn;
	}
	public void setShowRightBtn(boolean showRightBtn) {
		this.showRightBtn = showRightBtn;
	}
	public String getRightText() {
		return rightText;
	}
	public void setRightText(String rightText) {
		this.rightText = rightText;
	}

	@Override
	public String toString() {
		return "SettingType{" +
				"leftIcon=" + leftIcon +
				", leftText=" + leftText +
				", showRightBtn=" + showRightBtn +
				", rightText='" + rightText + '\'' +
				'}';
	}
}
