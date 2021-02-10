package com.daniel.final_project.interfaces.buyer;

import com.daniel.final_project.objects.Order;
import com.daniel.final_project.objects.Product;

import java.util.List;

public interface BuyerOrderCallBack {
    void putOrdersInList(List<Order> Orders);
}
