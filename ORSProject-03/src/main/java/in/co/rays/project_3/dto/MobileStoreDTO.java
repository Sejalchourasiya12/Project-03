package in.co.rays.project_3.dto;

/**
 * MobileStoreDTO - Data Transfer Object for Mobile Store
 *
 * @author Sejal Chourasiya
 */
public class MobileStoreDTO extends BaseDTO {

    private String brandName;
    private String modelName;
    private String ramSize;
    private String price;

    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getRamSize() {
        return ramSize;
    }
    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return brandName;
    }
}