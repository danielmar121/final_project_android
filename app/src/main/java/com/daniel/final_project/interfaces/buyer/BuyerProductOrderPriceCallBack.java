package com.daniel.final_project.interfaces.buyer;

public interface BuyerProductOrderPriceCallBack {
    void raisePrice(Double price);

    void decreasePrice(Double price);
}
