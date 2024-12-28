package org.library.library.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuantityAdjustmentDto {
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Adjustment type is required")
    private AdjustmentType type;

    @Getter
    public enum AdjustmentType {
        INCREASE("Increase"),    // For adding new stock
        DECREASE("Decrease"),    // For removing stock (sales, damage, etc)
        SET("Set");             // For setting absolute quantity

        private final String displayName;

        AdjustmentType(String displayName) {
            this.displayName = displayName;
        }

    }
}