/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xdtic.projpool.model;

/**
 *
 * @author joelorrala
 */
public class Post implements MessageContent{

    @Override
    public String getMessageContent(String proName) {
        StringBuilder content = new StringBuilder(50);
        content.append("棒棒哒~ 已经发布项目【").append(proName).append("】，请等待审核");
        return content.toString();
    }
    
}
