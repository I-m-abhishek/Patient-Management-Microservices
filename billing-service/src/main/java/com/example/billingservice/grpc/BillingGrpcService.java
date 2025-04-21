package com.example.billingservice.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest , StreamObserver<billing.BillingResponse> responseObserver) {

        log.info("create billing Account info" + billingRequest.toString());

        BillingResponse billingResponse = BillingResponse.newBuilder().setAccountId("12345").setStatus("Active").build();

        responseObserver.onNext(billingResponse);
        responseObserver.onCompleted();

    }
}
