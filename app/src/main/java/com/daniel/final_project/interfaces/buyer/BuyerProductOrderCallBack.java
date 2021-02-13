package com.daniel.final_project.interfaces.buyer;

import com.daniel.final_project.objects.ProductOrder;

import java.util.List;

public interface BuyerProductOrderCallBack {
    void putProductOrdersInList(List<ProductOrder> productOrders);
}
