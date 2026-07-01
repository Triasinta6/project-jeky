package com.jeky.backend.dto;

public class DashboardStatsResponse {
    private final long totalLayanan;
    private final long totalOrder;
    private final long waitingOrder;
    private final long acceptedOrder;
    private final long onProgressOrder;
    private final long completedOrder;
    private final long cancelledOrder;

    public DashboardStatsResponse(
            long totalLayanan,
            long totalOrder,
            long waitingOrder,
            long acceptedOrder,
            long onProgressOrder,
            long completedOrder,
            long cancelledOrder
    ) {
        this.totalLayanan = totalLayanan;
        this.totalOrder = totalOrder;
        this.waitingOrder = waitingOrder;
        this.acceptedOrder = acceptedOrder;
        this.onProgressOrder = onProgressOrder;
        this.completedOrder = completedOrder;
        this.cancelledOrder = cancelledOrder;
    }

    public long getTotalLayanan() {
        return totalLayanan;
    }

    public long getTotalOrder() {
        return totalOrder;
    }

    public long getWaitingOrder() {
        return waitingOrder;
    }

    public long getAcceptedOrder() {
        return acceptedOrder;
    }

    public long getOnProgressOrder() {
        return onProgressOrder;
    }

    public long getCompletedOrder() {
        return completedOrder;
    }

    public long getCancelledOrder() {
        return cancelledOrder;
    }
}
