package com.ace.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author bamboo
 */
@Data
@AllArgsConstructor
public class Msg {
    private String title;
    private String content;
    private String extraInfo;
}
