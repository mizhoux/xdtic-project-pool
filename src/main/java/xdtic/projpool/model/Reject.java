/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xdtic.projpool.model;

/**
 *
 * @author joelorrala
 */
public class Reject implements MessageContent{

    @Override
    public String getMessageContent(String proName){
        StringBuilder content = new StringBuilder(50);
        content.append("注意了~ 项目【").append(proName).append("】被拒绝了，请修改后重新提交");
        return content.toString();
    }
    
    
}
