package com.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


public class MessageServlet extends HttpServlet {
    private ObjectMapper mapper;
    private TaskManager taskManager = new TaskManager();

    @Override
    public void init() {
        mapper = Config.getMapper();
        taskManager.runTask();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ScheduledMessage model = mapper.readValue(body, ScheduledMessage.class);
        LocalDateTime localDateTime = LocalDateTime.parse(model.getTime());
        if (model.getTime() != null && localDateTime.isBefore(LocalDateTime.now())) {
            ErrorResponse response = new ErrorResponse(Config.ErrorCode.BAD_REQUEST.getCode(), "Invalid date: input date is earlier than now");
            resp.getOutputStream().print(mapper.writeValueAsString(response));
        } else if (model.getTime() != null) {
            taskManager.db.add(model);
            ErrorResponse response = new ErrorResponse(Config.ErrorCode.SUCCESS.getCode(), "Successfully scheduled message");
            resp.getOutputStream().print(mapper.writeValueAsString(response));
        } else {
            ErrorResponse response = new ErrorResponse(Config.ErrorCode.BAD_REQUEST.getCode(), "Invalid date: no date provided");
            resp.getOutputStream().print(mapper.writeValueAsString(response));
        }
    }
}
