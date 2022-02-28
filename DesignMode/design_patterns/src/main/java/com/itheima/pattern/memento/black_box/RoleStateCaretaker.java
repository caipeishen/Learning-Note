package com.itheima.pattern.memento.black_box;


/**
 * @version v1.0
 * @ClassName: RoleStateCaretaker
 * @Description: 备忘录对象管理对象
 * @Author: 黑马程序员
 */
public class RoleStateCaretaker {

    //声明RoleStateMemento类型的变量
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
