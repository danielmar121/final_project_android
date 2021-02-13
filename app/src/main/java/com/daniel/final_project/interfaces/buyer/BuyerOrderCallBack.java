package com.daniel.final_project.interfaces.buyer;

import com.daniel.final_project.objects.Order;

import java.util.List;

public interface BuyerOrderCallBack {
    void putOrdersInList(List<Order> orders);
}
