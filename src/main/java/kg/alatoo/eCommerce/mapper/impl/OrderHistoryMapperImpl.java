package kg.alatoo.eCommerce.mapper.impl;

import kg.alatoo.eCommerce.dto.cart.OrderHistoryResponse;
import kg.alatoo.eCommerce.dto.cart.PurchaseDetails;
import kg.alatoo.eCommerce.entity.Cart;
import kg.alatoo.eCommerce.entity.Purchase;
import kg.alatoo.eCommerce.mapper.OrderHistoryMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderHistoryMapperImpl implements OrderHistoryMapper {
    @Override
    public OrderHistoryResponse toDto(Cart cart) {
        List<PurchaseDetails> purchaseInfo = new ArrayList<>();
        for(Purchase element : cart.getOrderHistory().getPurchases()){
            PurchaseDetails purchaseDetails = new PurchaseDetails();
            purchaseDetails.setId(element.getId());
            purchaseDetails.setCost(element.getPrice());
            purchaseDetails.setDate(element.getDate());
            purchaseInfo.add(purchaseDetails);
        }
        OrderHistoryResponse response = new OrderHistoryResponse();
        response.setId(cart.getOrderHistory().getId());
        response.setPurchases(purchaseInfo);
        return response;
    }
}
