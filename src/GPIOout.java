import java.awt.Color;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

public class GPIOout{
	public GPIOout() throws InterruptedException {
		final GpioController gpio = GpioFactory.getInstance();
		// softPwmCreate(int pin, int value, int range)
		// the range is set like (min=0 ; max=100)
		SoftPwm.softPwmCreate(0, 0, 255);
		SoftPwm.softPwmCreate(3, 0, 255);
		SoftPwm.softPwmCreate(5, 0, 255);
		System.out.println("hi");
		

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        myButton.setShutdownOptions(true);

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
            	if(event.getState().equals(PinState.HIGH)) {
            		System.out.println(event.getPin()+","+event.getState());
                    skip();
            	}
            }
        });
	}
	private void skip() {
		if(Main.player.clip.isOpen())
			Main.player.stop();
		Main.shuffled = true;
		Main.player.setSong(Action3.fileIndex.getRandom());
		Main.player.play();
		Main.autoPlay = true;
	}
	private int last1 = 0;
	private boolean within(int i) {
		if(Math.abs(i-last1)>=10){
			last1 = i;
			return false;
		}
		return true;
	}
	public void set(int p1, int p2, int p3) {
		if(within(p1))return;
		System.out.println("output!");
		SoftPwm.softPwmWrite(0, (int)(p1*(188.0/265.0)+77));
		SoftPwm.softPwmWrite(3, (p2>77)?(int)(p2*(188.0/265.0)+77):0);
		SoftPwm.softPwmWrite(5, (int)(p3*(188.0/265.0)+77));
	}
	public void set(Color c) {
		set(c.getRed(),c.getGreen(),c.getBlue());
	}
}
