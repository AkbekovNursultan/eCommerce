package kg.alatoo.eCommerce.mapper.impl;

import kg.alatoo.eCommerce.dto.cart.CartElementResponse;
import kg.alatoo.eCommerce.dto.cart.CartInfoResponse;
import kg.alatoo.eCommerce.entity.Cart;
import kg.alatoo.eCommerce.entity.CartElement;
import kg.alatoo.eCommerce.mapper.CartMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartMapperImpl implements CartMapper {
    @Override
    public CartInfoResponse toDto(Cart cart) {
        List<CartElementResponse> elementsInfo = new ArrayList<>();
        for(CartElement element : cart.getProductsList()){
            CartElementResponse elementResponse = new CartElementResponse();
            elementResponse.setId(element.getId());
            elementResponse.setPrice(element.getPrice());
            elementResponse.setQuantity(element.getQuantity());
            elementResponse.setTitle(element.getTitle());
            elementResponse.setTotal(element.getTotal());
            elementsInfo.add(elementResponse);
        }
        CartInfoResponse response = new CartInfoResponse();
        response.setId(cart.getId());
        response.setList(elementsInfo);
        response.setTotalPrice(cart.getPrice());
        return response;
    }
}
