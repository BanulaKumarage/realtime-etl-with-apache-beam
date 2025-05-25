package com.springbootprojects.transforms;

import com.springbootprojects.models.IotEvent;
import org.apache.beam.sdk.transforms.DoFn;

public class IOEventsPrinter extends DoFn<IotEvent, IotEvent> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        IotEvent event = c.element();
        System.out.println(event.toString());
        c.output(event);
    }

}
