package com.epam.spring.core.loggers;

import com.epam.spring.core.beans.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class DBLogger implements EventLogger {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void logEvent(Event event) throws IOException {
        jdbcTemplate.update("INSERT INTO t_event (id, msg) VALUES (?,?)",
                event.getId(), event.getMsg());
    }

    public int eventsCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM t_event", Integer.class);
    }

    public String getMsgById(int eventId) {
        return jdbcTemplate.queryForObject("SELECT msg FROM t_event WHERE id = ?", new Object[]{eventId}, String.class);
    }

    public List<Event> allEvents() {
        return jdbcTemplate.query("SELECT * FROM t_event", (rs, rowNum) -> {
            Event event = new Event(new Date(), DateFormat.getDateInstance());
            event.setId(rs.getInt("id"));
            event.setMsg(rs.getString("msg"));
            return event;
        });
    }
}
