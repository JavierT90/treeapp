package com.treeapp.objects;

public class Status {

	private String Status;
	private int sourceImage;

	public Status(String Status, int sourceImage) {
		this.Status = Status;
		this.sourceImage = sourceImage;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public int getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(int sourceImage) {
		this.sourceImage = sourceImage;
	}
}
