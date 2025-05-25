package com.springbootprojects.transforms;

import com.springbootprojects.models.IotEvent;
import org.apache.beam.sdk.transforms.DoFn;

public class EventsFilter extends DoFn<IotEvent, String> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        IotEvent event = c.element();
        if (event.getTemperature() > 80) {
            c.output(event.getDeviceId());
        }
    }
}
