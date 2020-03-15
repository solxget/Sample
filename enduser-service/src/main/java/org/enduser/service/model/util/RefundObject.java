package org.enduser.service.model.util;

import org.springframework.stereotype.Component;

/**
 * @author solxget
 * 
 */
@Component
public class RefundObject {
    private long amount;
    private String paymentId;
    private String reason;
    private String referance;

    private String touristEmailAddress;

    public RefundObject() {}

    public RefundObject(long amount, String paymentId, String reason, String referance, String touristEmailAddress) {
        super();
        this.amount = amount;
        this.paymentId = paymentId;
        this.reason = reason;
        this.referance = referance;
        this.touristEmailAddress = touristEmailAddress;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReferance() {
        return referance;
    }

    public void setReferance(String referance) {
        this.referance = referance;
    }

    public String getTouristEmailAddress() {
        return touristEmailAddress;
    }

    public void setTouristEmailAddress(String touristEmailAddress) {
        this.touristEmailAddress = touristEmailAddress;
    }

    @Override
    public String toString() {
        return "RefundObject [amount=" + amount + ", paymentId=" + paymentId + ", reason=" + reason + ", referance=" + referance
                + ", touristEmailAddress=" + touristEmailAddress + "]";
    }

}
