/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xdtic.projpool.model;

/**
 *
 * @author joelorrala
 */
public class Join implements MessageContent{

    @Override
    public String getMessageContent(String proName){
        StringBuilder content = new StringBuilder(50);
        content.append("好开心~ 有用户报名了项目【").append(proName).append("】");
        return content.toString();
    }
    
}
