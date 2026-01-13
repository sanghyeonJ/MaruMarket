package model;

public class ProductFileDto {
	private int productFileId;
    private int productId;
    private int fileId;
    private String fileType; // MAIN, DETAIL
    private int sortOrder;
    
	public int getProductFileId() {
		return productFileId;
	}
	public void setProductFileId(int productFileId) {
		this.productFileId = productFileId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
    
}
