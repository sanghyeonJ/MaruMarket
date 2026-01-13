package model;

import java.util.List;

public class ProductViewDto {
	private ProductDto product;
    private FileDto mainImage;
    private List<FileDto> detailImages;
    
	public ProductDto getProduct() {
		return product;
	}
	public void setProduct(ProductDto product) {
		this.product = product;
	}
	public FileDto getMainImage() {
		return mainImage;
	}
	public void setMainImage(FileDto mainImage) {
		this.mainImage = mainImage;
	}
	public List<FileDto> getDetailImages() {
		return detailImages;
	}
	public void setDetailImages(List<FileDto> detailImages) {
		this.detailImages = detailImages;
	}
    
}
