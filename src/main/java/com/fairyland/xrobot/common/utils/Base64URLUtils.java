package com.fairyland.xrobot.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @program: fairyland->Base64URLUtils
 * @description: TODO
 * @author: ctc
 * @create: 2019-11-28 17:47
 **/
public class Base64URLUtils {

    private static Logger logger = LoggerFactory.getLogger(Base64URLUtils.class);

    /**
     * URLBase64加密
     */
    public static String encode(String data) throws UnsupportedEncodingException {
        byte[] encodedByte = Base64.encodeBase64URLSafe(data.getBytes("UTF-8"));
        return new String(encodedByte, "UTF-8");
    }

    /**
     * URLBase64解密
     */
    public static String decode(String data) throws UnsupportedEncodingException {
        byte[] decodedByte = Base64.decodeBase64(data.getBytes("UTF-8"));


        return new String(decodedByte, "UTF-8");
    }


    public static void main(String[] args) throws UnsupportedEncodingException {

        String aa = "eyJsb2dpbl91c2VyIjp7InRva2VuIjoiYXV0aF90b2tlbl8xX2VlYjAwYTk0LTg2NzItNDZlMy04NjU3LTZjY2JiNWU3MmU5NCIsImxvZ2luVGltZSI6MTU3NTA4ODc1MTY3MSwiZXhwaXJlVGltZSI6MTU3NTE3NTE1MTY3MSwiaXBhZGRyIjoiMTI3LjAuMC4xIiwibG9naW5Mb2NhdGlvbiI6IuWGhee9kUlQIiwiYnJvd3NlciI6IkNocm9tZSIsIm9zIjoiV2luZG93cyAxMCIsInBlcm1pc3Npb25zIjpbIio6KjoqIl0sInVzZXIiOnsic2VhcmNoVmFsdWUiOm51bGwsImNyZWF0ZUJ5IjoiYWRtaW4iLCJjcmVhdGVUaW1lIjoiMjAxOC0wMy0xNiAwMzozMzowMCIsInVwZGF0ZUJ5IjpudWxsLCJ1cGRhdGVUaW1lIjpudWxsLCJyZW1hcmsiOiLnrqHnkIblkZgiLCJkYXRhU2NvcGUiOm51bGwsInVzZXJJZCI6MSwiZGVwdElkIjoxMDMsInVzZXJOYW1lIjoiYWRtaW4iLCJuaWNrTmFtZSI6IuiLpeS-nSIsImVtYWlsIjoicnlAMTYzLmNvbSIsInBob25lbnVtYmVyIjoiMTU4ODg4ODg4ODgiLCJzZXgiOiIxIiwiYXZhdGFyIjoiIiwicGFzc3dvcmQiOiIkMmEkMTAkN0pCNzIweXViVlNadlVJMHJFcUsvLlZxR09aVEgudWx1MzNkSE9pQkU4QnlPaEpJcmRBdTIiLCJzYWx0IjpudWxsLCJzdGF0dXMiOiIwIiwiZGVsRmxhZyI6IjAiLCJsb2dpbklwIjoiMTI3LjAuMC4xIiwibG9naW5EYXRlIjoxNTIxMTcxMTgwMDAwLCJkZXB0Ijp7InNlYXJjaFZhbHVlIjpudWxsLCJjcmVhdGVCeSI6bnVsbCwiY3JlYXRlVGltZSI6bnVsbCwidXBkYXRlQnkiOm51bGwsInVwZGF0ZVRpbWUiOm51bGwsInJlbWFyayI6bnVsbCwiZGF0YVNjb3BlIjpudWxsLCJkZXB0SWQiOjEwMywicGFyZW50SWQiOjEwMSwiYW5jZXN0b3JzIjpudWxsLCJkZXB0TmFtZSI6IueglOWPkemDqOmXqCIsIm9yZGVyTnVtIjoiMSIsImxlYWRlciI6IuiLpeS-nSIsInBob25lIjpudWxsLCJlbWFpbCI6bnVsbCwic3RhdHVzIjoiMCIsImRlbEZsYWciOm51bGwsInBhcmVudE5hbWUiOm51bGwsImNoaWxkcmVuIjpbXX0sInJvbGVzIjpbeyJzZWFyY2hWYWx1ZSI6bnVsbCwiY3JlYXRlQnkiOm51bGwsImNyZWF0ZVRpbWUiOm51bGwsInVwZGF0ZUJ5IjpudWxsLCJ1cGRhdGVUaW1lIjpudWxsLCJyZW1hcmsiOm51bGwsImRhdGFTY29wZSI6IjEiLCJyb2xlSWQiOjEsInJvbGVOYW1lIjoi566h55CG5ZGYIiwicm9sZUtleSI6ImFkbWluIiwicm9sZVNvcnQiOiIxIiwic3RhdHVzIjoiMCIsImRlbEZsYWciOm51bGwsImZsYWciOmZhbHNlLCJtZW51SWRzIjpudWxsLCJkZXB0SWRzIjpudWxsLCJhZG1pbiI6dHJ1ZX1dLCJyb2xlSWRzIjpudWxsLCJwb3N0SWRzIjpudWxsLCJhZG1pbiI6dHJ1ZX0sInVzZXJuYW1lIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6bnVsbH0sImxvZ2luX2Jyb3dzZXIiOiJDaHJvbWUiLCJsb2dpbl9vcyI6IldpbmRvd3MgMTAiLCJsb2dpbl9pcCI6IjEyNy4wLjAuMSIsImxvZ2luX3VzZXJfa2V5IjoiYXV0aF90b2tlbl8xX2VlYjAwYTk0LTg2NzItNDZlMy04NjU3LTZjY2JiNWU3MmU5NCJ9";


        System.out.println(decode(aa));

    }
}
