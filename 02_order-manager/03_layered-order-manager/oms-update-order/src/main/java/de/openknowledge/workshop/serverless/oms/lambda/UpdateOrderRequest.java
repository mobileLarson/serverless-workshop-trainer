/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package de.openknowledge.workshop.serverless.oms.lambda;

import de.openknowledge.workshop.serverless.oms.model.Order;

/**
 * Wrapper object for order information
 */
public class UpdateOrderRequest {

    private String orderId;
    private Long orderNo;
    private String orderStatus;
    private String userId;
    private String baristaId;
    private String drink;

    public UpdateOrderRequest() {
    }

    public UpdateOrderRequest(String orderId, Long orderNo, String orderStatus, String userId, String baristaId, String drink) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.baristaId = baristaId;
        this.drink = drink;
    }

    public Order getOrder() {
        return new Order(orderId,orderNo,orderStatus,userId,baristaId,drink);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBaristaId() {
        return baristaId;
    }

    public void setBaristaId(String baristaId) {
        this.baristaId = baristaId;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public boolean isValid() {
        return  orderId != null;
    }
}
