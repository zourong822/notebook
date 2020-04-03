package com.company.test;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 1.生产面包
 * 2.涂黄油
 * 3.再涂果酱
 *
 */
class Toast{
    public enum Status{DRY,BUTTERD,JAMMED}
    private Status state=Status.DRY;
    private final int id;
    public Toast(int id){
        this.id=id;
    }
    public void butterd(){
        state=Status.BUTTERD;
    }
    public void jammed(){
        state=Status.JAMMED;
    }
    public Status getState(){
        return state;
    }
    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "Toast{" +
                "state=" + state +
                ", id=" + id +
                '}';
    }
}
class Toaster implements Runnable{
    private LinkedBlockingQueue<Toast> drys;
    private int count;
    private Random rand=new Random(47);

    public Toaster(LinkedBlockingQueue dry) {
        this.drys = dry;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(100+rand.nextInt(500));
                Toast t=new Toast(count++);
                System.out.print(t);
                drys.put(t);
                System.out.println(drys.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Dry off...");
    }
}
class Butterd implements Runnable{
    private LinkedBlockingQueue<Toast> drys;
    private LinkedBlockingQueue<Toast> butterds;

    public Butterd(LinkedBlockingQueue drys, LinkedBlockingQueue butterds) {
        this.drys = drys;
        this.butterds = butterds;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast t = drys.take();
                t.butterd();
                System.out.print(t);
                butterds.put(t);
                System.out.println(butterds.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Butterd off...");
    }
}

class Jammed implements Runnable{
    private LinkedBlockingQueue<Toast> butterds;
    private LinkedBlockingQueue<Toast> jammeds;

    public Jammed(LinkedBlockingQueue<Toast> butterds, LinkedBlockingQueue<Toast> jammeds) {
        this.butterds = butterds;
        this.jammeds = jammeds;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast t = butterds.take();
                t.jammed();
                System.out.print(t);
                jammeds.put(t);
                System.out.println(jammeds.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Jammed off...");
    }
}
public class TestBlockingQueue {
    public static void main(String[] args) {
        ThreadPoolExecutor tpe=new ThreadPoolExecutor(5,10,3,TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        LinkedBlockingQueue<Toast> drys=new LinkedBlockingQueue<>(),
                                    butterds=new LinkedBlockingQueue<>(),
                                    jammeds=new LinkedBlockingQueue<>();
        tpe.execute(new Toaster(drys));
        tpe.execute(new Butterd(drys,butterds));
        tpe.execute(new Jammed(butterds,jammeds));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //interrupt all threads;
        tpe.shutdownNow();
    }



}
