package dk_kea.llmbackendproject.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Products {

    private List<ProductDTO> products;

    public static class ProductDTO {
        private String templateName;
        private String youtubeId;
        private String youtubeThumbnailImage;
        private String primaryImage;
        private AvailabilityDTO availability;
        private String id;
        private String name;
        private String category;
        private String brand;
        private String subCategory;
        private String url;
        private String unitPrice;
        private double unitPriceCalc;
        private String unitPriceLabel;
        private boolean discountItem;
        private String description;
        private int saleBeforeLastSalesDate;
        private double price;
        private Object campaign;  // If you know the type of 'campaign', replace Object with the appropriate type.
        private List<String> labels;
        private boolean favorite;
        private String productSubGroupNumber;
        private String productSubGroupName;
        private String productCategoryGroupNumber;
        private String productCategoryGroupName;
        private String productMainGroupNumber;
        private String productMainGroupName;

        // Getters and Setters
        // ... Include getters and setters for all fields.
    }

    public static class AvailabilityDTO {
        private boolean isDeliveryAvailable;
        private boolean isAvailableInStock;
        private List<Object> reasonMessageKeys;

        // Getters and Setters
        public boolean isDeliveryAvailable() {
            return isDeliveryAvailable;
        }

        public void setDeliveryAvailable(boolean deliveryAvailable) {
            isDeliveryAvailable = deliveryAvailable;
        }

        public boolean isAvailableInStock() {
            return isAvailableInStock;
        }

        public void setAvailableInStock(boolean availableInStock) {
            isAvailableInStock = availableInStock;
        }

        public List<Object> getReasonMessageKeys() {
            return reasonMessageKeys;
        }

        public void setReasonMessageKeys(List<Object> reasonMessageKeys) {
            this.reasonMessageKeys = reasonMessageKeys;
        }
    }
}
