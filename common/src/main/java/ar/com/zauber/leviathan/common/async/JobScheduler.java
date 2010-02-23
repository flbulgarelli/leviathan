/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.leviathan.common.async;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <p>
 * Consume FetchQueue y los pone a ejecutar en un ExecutorService.
 * Exista para garantizar algunas cosas. Si esto fuera directamente un
 * executorservice, los trabajos podrian ir directamente al thread y no a la cola, y 
 * eso no nos permitaria tener politicas de polite.  
 * </p>
 * 
 * <p>
 * Se puede especificar un Timer y un timeout; los cuales limitan el tiempo de 
 * ejecuci�n de los {@link Job}. Hay que recordar que estos deben ser interruptibles.
 * </p>
 * 
 * @author Juan F. Codagnone
 * @since Feb 16, 2010
 */
public class JobScheduler implements Runnable {
    private final JobQueue queue;
    private final ExecutorService executorService;
    private final Logger logger = Logger.getLogger(JobScheduler.class);
    private Timer timer;
    private long timeout = 0;
    
    /** Creates the FetcherScheduler. */
    public JobScheduler(final JobQueue queue, 
            final ExecutorService executorService) {
        this(queue, executorService, null, 0);
    }
    
    /** Creates the FetcherScheduler. */
    public JobScheduler(final JobQueue queue, 
            final ExecutorService executorService,
            final Timer timer, final long timeout) {
        Validate.notNull(queue);
        Validate.notNull(executorService);
        if(timer != null) {
            Validate.isTrue(timeout > 0);
        }
        
        this.queue = queue;
        this.executorService = executorService;
        this.timer = timer;
        this.timeout = timeout;
        
    }
    

    /** @see Runnable#run() */
    public final void run() {
        while(true) {
            try {
                final Job job = queue.poll();
                final Future<?> future = executorService.submit(job);
                if(timer != null && !future.isDone()) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if(!future.isDone()) {
                                future.cancel(true);
                                if(logger.isDebugEnabled()) {
                                    logger.debug("Timeout while processing job "
                                            + job);
                                }
                            }
                        }
                    }, timeout);
                }
            } catch (final InterruptedException e) {
                if(queue.isShutdown()) {
                    logger.info("Interrupted poll(). Queue is shutting down");
                } else {
                    logger.log(Level.WARN, 
                            "Interrupted poll() but we are not shutting down!", e);
                }
                break;
            }
        }
        // si es que nos encontramos aqui ya no qued� nada por consumir  
        executorService.shutdown();
        
        while(!executorService.isTerminated()) {
            try {
                executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (final InterruptedException e) {
                logger.log(Level.WARN, 
                  "Interrupted awaitTermination on FetcherScheduler shutdown", e);
            }
        }
    }
    
    /** Returns the queue. */
    public final JobQueue getQueue() {
        return queue;
    }
}
