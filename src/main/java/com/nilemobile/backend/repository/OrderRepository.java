package com.nilemobile.backend.repository;

import com.nilemobile.backend.contant.OrderStatus;
import com.nilemobile.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Override
    List<Order> findAll();

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.customer.user.userId = :userId")
    List<Order> findByUserId(Long userId);

    @Query("""
            SELECT COUNT(od) > 0
            FROM OrderDetail od
            WHERE od.variation.variationId = :variationId
              AND od.order.customer.user.userId = :userId
              AND od.order.status IN :statuses
            """)
    boolean existsPurchasedVariation(@Param("userId") Long userId,
                                     @Param("variationId") Long variationId,
                                     @Param("statuses") Collection<OrderStatus> statuses);
}
