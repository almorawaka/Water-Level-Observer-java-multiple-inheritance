import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
interface WaterLevelObserver {  
    public void update(int waterLevel);
    
}
class SMSWindow extends JFrame implements WaterLevelObserver{
    private JLabel smsLabel;
    SMSWindow(){
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("SMS Window");
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        //-----------------------------------------
        smsLabel=new JLabel("0");
        smsLabel.setFont(new Font("",1,25));
        add(smsLabel);
        setVisible(true);
    }
    public void update(int waterLevel){
        smsLabel.setText("Sending SMS : "+waterLevel);
    }
}
class DisplayWindow extends JFrame implements WaterLevelObserver{
    private JLabel displayLabel;
    DisplayWindow(){
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Display Window");
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        //-----------------------------------------
        displayLabel=new JLabel("0");
        displayLabel.setFont(new Font("",1,25));
        add(displayLabel);
        setVisible(true);
    }
    public void update(int waterLevel){
        displayLabel.setText(waterLevel+""); //Integer.toString(waterLevel)
    }
}
class AlarmWindow extends JFrame implements WaterLevelObserver{
    private JLabel alarmLabel;
    AlarmWindow(){
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Alarm Window");
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        //-----------------------------------------
        alarmLabel=new JLabel("OFF");
        alarmLabel.setFont(new Font("",1,25));
        add(alarmLabel);
        setVisible(true);
    }
    public void update(int waterLevel){
        alarmLabel.setText(waterLevel>=50 ? "ON":"OFF");
    }
}
class SplitterWindow  extends JFrame implements WaterLevelObserver{
    private JLabel splitterLabel;
    SplitterWindow(){
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Splitter Window");
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        //-----------------------------------------
        splitterLabel=new JLabel("OFF");
        splitterLabel.setFont(new Font("",1,25));
        add(splitterLabel);
        setVisible(true);
    }
    public void update(int waterLevel){
        splitterLabel.setText(waterLevel>=75 ? "Splitter ON":"Splitter OFF");
    }
}
class WaterTankWindow extends JFrame{
    private WaterTankController waterTankController;
    
    private JSlider waterLevelSlider;
    WaterTankWindow(WaterTankController waterTankController){
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("WaterTank Window");
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        
        this.waterTankController=waterTankController;
        //-----------------------------------------
        waterLevelSlider=new JSlider(JSlider.VERTICAL,0,100,50);
        waterLevelSlider.setFont(new Font("",1,25));
        waterLevelSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                int waterLevel=waterLevelSlider.getValue();
                waterTankController.setWaterLevel(waterLevel);
            }
        });
        add(waterLevelSlider);
    }
}
class WaterTankController{
    private WaterLevelObserver[] observerArray=new WaterLevelObserver[0];
    
    private int waterLevel;
    
    public void addWaterLevelObserver(WaterLevelObserver waterLevelObserver){
        WaterLevelObserver[] temp=new WaterLevelObserver[observerArray.length+1];
        for(int i=0; i<observerArray.length; i++){
            temp[i]=observerArray[i];
        }
        temp[observerArray.length]=waterLevelObserver;
        observerArray=temp;
    }
    public void setWaterLevel(int waterLevel){
        if(this.waterLevel!=waterLevel){
            this.waterLevel=waterLevel;
            notifyObservers();
        }
    }
    public void notifyObservers(){
        for(WaterLevelObserver ob : observerArray){
            ob.update(waterLevel);
        }
    }
}
class Demo{ 
    public static void main(String args[]){   
        WaterTankController waterTankController=new WaterTankController();
        waterTankController.addWaterLevelObserver(new AlarmWindow());
        waterTankController.addWaterLevelObserver(new DisplayWindow());
        waterTankController.addWaterLevelObserver(new SplitterWindow());
        waterTankController.addWaterLevelObserver(new SMSWindow());
        new WaterTankWindow(waterTankController).setVisible(true);
    } 
}
