package com.tomhurry.email;

import javax.mail.MessagingException;

/**
 * 邮件发送接口
 *
 * @author taozhi
 * @date 2021/1/31
 * @since 1.0.0
 */
public interface MailSender {


    /**
     * 发送文本邮件
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     */
    void sendSimpleMail(String to, String subject, String content, String... cc);

    /**
     * 发送Html邮件
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     */
    void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException;

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param filePath 附件地址
     * @param cc       抄送地址
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) throws MessagingException;

    /**
     * 发送正文中有静态资源的邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param rscPath 静态资源地址
     * @param rscId   静态资源id
     * @param cc      抄送地址
     * @throws MessagingException 邮件发送异常
     */
    void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) throws MessagingException;
}
