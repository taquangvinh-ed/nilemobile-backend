package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.contant.OrderStatus;
import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.mapper.OrderMapper;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.reponse.OrderDTO;
import com.nilemobile.backend.repository.OrderRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.service.CartItemService;
import com.nilemobile.backend.service.CartService;
import com.nilemobile.backend.service.OrderService;
import com.nilemobile.backend.service.ProductService;
import com.nilemobile.backend.specification.OrderSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {


    private CartRepository cartRepository;


    private CartService cartService;


    private ProductService productService;


    private OrderRepository orderRepository;

    private VariationRepository variationRepository;

    private CartItemService cartItemService;

    public OrderServiceImp(CartRepository cartRepository,
                           CartService cartService,
                           ProductService productService,
                           OrderRepository orderRepository,
                           VariationRepository variationRepository,
                           CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.variationRepository = variationRepository;
        this.cartItemService = cartItemService;
    }

//    @Override
//    public Order createOrder(User user, Address shippingAddress, List<Map<String, Object>> selectedItems) {
//        if (selectedItems == null || selectedItems.isEmpty()) {
//            throw new Orderexception("Không có sản phẩm nào được chọn để tạo đơn hàng!");
//        }
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setShippingAddress(shippingAddress);
//        order.setOrderDate(LocalDateTime.now());
//        order.setCreateAt(LocalDateTime.now());
//        order.setStatus(OrderStatus.PLACED);
//
//        // Tính toán tổng giá, tổng giảm giá, và tổng số lượng dựa trên selectedItems
//        long totalPrice = 0;
//        long totalDiscount = 0;
//        int totalItem = 0;
//        List<OrderDetail> orderDetails = new ArrayList<>();
//
//        for (Map<String, Object> item : selectedItems) {
//            // Kiểm tra item không null
//            if (item == null) {
//                throw new Orderexception("Dữ liệu sản phẩm không hợp lệ: item là null");
//            }
//
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrder(order);
//
//            // Lấy variationMap và kiểm tra
//            Map<String, Object> variationMap = (Map<String, Object>) item.get("variation");
//            if (variationMap == null) {
//                throw new Orderexception("Dữ liệu variation không hợp lệ: variation là null");
//            }
//
//            Variation variation = new Variation();
//
//            // Kiểm tra và gán id (bắt buộc)
//            Object idObj = variationMap.get("variationId"); // Sửa: dùng "variationId" thay vì "id"
//            if (idObj == null) {
//                throw new Orderexception("ID của variation không được để trống");
//            }
//            variation.setId(Long.parseLong(idObj.toString()));
//
//            // Gán name (không bắt buộc, đã có giá trị mặc định từ VariationDTO)
//
//            // Gán color (không bắt buộc, có thể null)
//            variation.setColor((String) variationMap.get("color"));
//
//            // Kiểm tra và gán price (bắt buộc)
//            Object priceObj = variationMap.get("price");
//            if (priceObj == null) {
//                throw new Orderexception("Giá của variation không được để trống");
//            }
//            variation.setPrice(Long.parseLong(priceObj.toString()));
//
//            // Kiểm tra và gán discountPercent (bắt buộc, mặc định 0 nếu null)
//            Object discountPercentObj = variationMap.get("discountPercent");
//            variation.setDiscountPercent(discountPercentObj != null
//                    ? Integer.parseInt(discountPercentObj.toString())
//                    : 0);
//
//            Object discountPrice = variationMap.get("discountPrice");
//            if (discountPrice == null) {
//                throw new Orderexception("Giá sau khi giảm của variation không được để trống");
//            }
//            variation.setDiscountPrice(Long.parseLong(discountPrice.toString()));
//
//
//            // Gán imageURL (không bắt buộc, có thể null)
//            variation.setImageURL((String) variationMap.get("imageURL"));
//
//            orderDetail.setVariation(variation);
//
//            // Kiểm tra và gán quantity (bắt buộc)
//            Object quantityObj = item.get("quantity");
//            if (quantityObj == null) {
//                throw new Orderexception("Số lượng sản phẩm không được để trống");
//            }
//            orderDetail.setQuantity(Integer.parseInt(quantityObj.toString()));
//
//            // Kiểm tra và gán subtotal (bắt buộc)
//            Object subtotalObj = item.get("subtotal");
//            if (subtotalObj == null) {
//                throw new Orderexception("Tổng phụ của sản phẩm không được để trống");
//            }
//            orderDetail.setSubtotal(Long.parseLong(subtotalObj.toString()));
//
//            orderDetails.add(orderDetail);
//
//            totalPrice += orderDetail.getSubtotal();
//
//            // Kiểm tra và gán discountPrice (bắt buộc, mặc định 0 nếu null)
//            Object discountPriceObj = item.get("discountPrice");
//            if (discountPriceObj == null) {
//                throw new Orderexception("Giá giảm của sản phẩm không được để trống");
//            }
//            totalDiscount += Long.parseLong(discountPriceObj.toString());
//
//            totalItem += orderDetail.getQuantity();
//            orderDetail.setTotalDiscountPrice(variation.getDiscountPrice() * orderDetail.getQuantity());
//        }
//
//        order.setTotalPrice(totalPrice);
//        order.setTotalDiscountPrice(totalDiscount);
//        order.setTotalItem(totalItem);
//        order.setOrderDetails(orderDetails);
//
//        Order savedOrder = orderRepository.save(order);
//

    /// /         //Xóa các CartItem được chọn khỏi giỏ hàng
    /// /        for (Map<String, Object> item : selectedItems) {
    /// /            Object cartItemIdObj = item.get("id");
    /// /            if (cartItemIdObj == null) {
    /// /                throw new Orderexception("ID của CartItem không được để trống");
    /// /            }
    /// /            Long cartItemId = Long.parseLong(cartItemIdObj.toString());
    /// /            cartService.removeCartItem(user.getUserId(), cartItemId);
    /// /        }
//
//        return savedOrder;
//    }
    @Override
    public Order createOrder(User user, Address shippingAddress, List<Map<String, Object>> selectedItems) {
        if (selectedItems == null || selectedItems.isEmpty()) {
            throw new Orderexception("Không có sản phẩm nào được chọn để tạo đơn hàng!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(LocalDateTime.now());
        order.setCreateAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        long totalPrice = 0;
        long totalDiscount = 0;
        int totalItem = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (Map<String, Object> item : selectedItems) {
            if (item == null) {
                throw new Orderexception("Dữ liệu sản phẩm không hợp lệ: item là null");
            }

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            Map<String, Object> variationMap = (Map<String, Object>) item.get("variation");
            if (variationMap == null) {
                throw new Orderexception("Dữ liệu variation không hợp lệ: variation là null");
            }

            Object idObj = variationMap.get("variationId");
            if (idObj == null) {
                throw new Orderexception("ID của variation không được để trống");
            }
            Long variationId = Long.parseLong(idObj.toString());

            // Lấy Variation từ cơ sở dữ liệu
            Variation variation = variationRepository.findById(variationId)
                    .orElseThrow(() -> new Orderexception("Không tìm thấy Variation với ID: " + variationId));

            orderDetail.setVariation(variation);

            Object quantityObj = item.get("quantity");
            if (quantityObj == null) {
                throw new Orderexception("Số lượng sản phẩm không được để trống");
            }
            int quantity = Integer.parseInt(quantityObj.toString());
            orderDetail.setQuantity(quantity);

            // Tính subtotal dựa trên giá của Variation từ DB
            orderDetail.setSubtotal(variation.getPrice() * quantity);

            orderDetails.add(orderDetail);

            totalPrice += orderDetail.getSubtotal();
            totalDiscount += variation.getDiscountPrice() != null ? variation.getDiscountPrice() * quantity : 0;
            totalItem += quantity;
            orderDetail.setTotalDiscountPrice(variation.getDiscountPrice() != null ? variation.getDiscountPrice() * quantity : 0);
        }

        order.setTotalPrice(totalPrice);
        order.setTotalDiscountPrice(totalDiscount);
        order.setTotalItem(totalItem);
        order.setOrderDetails(orderDetails);

        Order savedOrder = orderRepository.save(order);
        for (Map<String, Object> item : selectedItems) {
            Object cartItemIdObj = item.get("id"); // Lấy id của CartItem từ selectedItems
            if (cartItemIdObj == null) {
                throw new Orderexception("ID của CartItem không được để trống");
            }
            Long cartItemId = Long.parseLong(cartItemIdObj.toString());
            cartItemService.removeCartItem(user.getUserId(), cartItemId);
        }

        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws Orderexception {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Orderexception("Không tìm thấy đơn hàng với ID: " + orderId));
    }

    @Override
    public List<Order> orderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }


    @Override
    public Order confirmOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.PLACED)) {
                throw new Orderexception("Đơn hàng khong thể được xác nhận vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.CONFIRMED);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order processOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.CONFIRMED)) {
                throw new Orderexception("Đơn hàng khong thể được xử lý vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.PROCESSING);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order shippedOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.PROCESSING)) {
                throw new Orderexception("Đơn hàng khong thể được xử lý vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.SHIPPED);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order deliveredOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.SHIPPED)) {
                throw new Orderexception("Đơn hàng khong thể xác nhận vận chuyển vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.DELIVERED);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order completeOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.DELIVERED)) {
                throw new Orderexception("Đơn hàng khong thể xác nhận hoàn thành  vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.COMPLETED);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order canceledOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.PLACED)) {
                throw new Orderexception("Đơn hàng không thể hủy được");
            }
            order.setStatus(OrderStatus.CANCELED);
            return orderRepository.save(order);
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void deleteOrder(Long orderId) throws Orderexception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Orderexception("Không tìm thấy đơn hàng với ID: " + orderId));

        if (!order.getStatus().equals(OrderStatus.PLACED) && !order.getStatus().equals(OrderStatus.CANCELED)) {
            throw new Orderexception("Không thể xóa đơn hàng vì trạng thái hiện tại là: " + order.getStatus() +
                    ". Chỉ có thể xóa đơn hàng ở trạng thái PLACED hoặc CANCELED.");
        }

        try {
            orderRepository.delete(order);
        } catch (Exception e) {
            throw new Orderexception("Lỗi khi xóa đơn hàng với ID: " + orderId + ". Chi tiết: " + e.getMessage());
        }
    }

    @Override
    public List<OrderDTO> filterOrderByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new Orderexception("Trạng thái không được để trống");
        }

        List<Order> orders = orderRepository.findByStatus(status);
        return OrderMapper.toDTOs(orders);
    }

    @Override
    public Order updateShippingAddress(Long orderId, Address shippingAddress) throws Orderexception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Orderexception("Không tìm thấy đơn hàng với ID: " + orderId));

        if (order.getStatus().equals(OrderStatus.SHIPPED) || order.getStatus().equals(OrderStatus.DELIVERED)) {
            throw new Orderexception("Không thể cập nhật địa chỉ cho đơn hàng đã giao.");
        }

        if (order.getShippingAddress() != null && order.getShippingAddress().equals(shippingAddress)) {
            return order; // Giữ nguyên đơn hàng hiện tại, không cần lưu lại
        }

        order.setShippingAddress(shippingAddress);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUserAndStatus(Long userId, String status) {
        Specification<Order> spec = Specification.where(OrderSpecification.hasUserId(userId))
                .and(OrderSpecification.hasStatus(status));
        return orderRepository.findAll(spec);
    }
}
