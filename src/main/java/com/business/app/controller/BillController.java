package com.business.app.controller;

import com.business.app.controller.dto.BillCreateDto;
import com.business.app.controller.dto.BillDto;
import com.business.app.controller.dto.BillSimpleDto;
import com.business.app.repository.model.Bill;
import com.business.app.repository.model.User;
import com.business.app.service.BillService;
import com.business.app.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bill")
@Api(tags = "bill")
@Log4j2
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ApiOperation(value = "create bill", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The user of bill does not exist"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BillSimpleDto create(@RequestBody(required = true) BillCreateDto billCreateDto, HttpServletRequest req) {
        log.info("Creating bill: {}", billCreateDto);
        User user = userService.findUserSession(req);
        log.info("Created By user: {}", user);
        Bill bill = modelMapper.map(billCreateDto, Bill.class);
        Bill createdBill = billService.create(bill);
        return modelMapper.map(createdBill, BillSimpleDto.class);
    }

    @PostMapping("/{billId}/pay")
    @ApiOperation(value = "pay bill", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Bill does not exist"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PreAuthorize("hasRole('ROLE_USER')")
    public BillDto billPay(@PathVariable(required = true) Long billId, HttpServletRequest req) {
        log.info("Pay bill: {}", billId);
        User user = userService.findUserSession(req);
        Bill savedBill = billService.billPay(billId, user.getId());
        return modelMapper.map(savedBill, BillDto.class);
    }

    @GetMapping("/my-bills")
    @ApiOperation(value = "list my bills", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BillDto> listMyBills(HttpServletRequest req) {
        log.info("Listing My Bills");
        User user = userService.findUserSession(req);
        log.info("Logged user id: {}", user);
        Type listType = new TypeToken<List<BillDto>>(){}.getType();
        return modelMapper.map(billService.listByUserId(user.getId()), listType);
    }

    @GetMapping("/user-id/{userId}")
    @ApiOperation(value = "list bills by userId", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<BillDto> listByUserId(@PathVariable(required = true) Long userId) {
        log.info("Listing Bills By User Id: {}", userId);
        Type listType = new TypeToken<List<BillDto>>(){}.getType();
        return modelMapper.map(billService.listByUserId(userId), listType);
    }

    @GetMapping
    @ApiOperation(value = "list all bills", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<BillDto> listAll() {
        log.info("Listing All Bills");
        Type listType = new TypeToken<List<BillDto>>(){}.getType();
        return modelMapper.map(billService.listAll(), listType);
    }
}
