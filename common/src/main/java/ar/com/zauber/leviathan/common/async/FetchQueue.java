/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.leviathan.common.async;

import java.util.concurrent.RejectedExecutionException;

/**
 * Cola de trabajos de Fetching. La implementaci�n DEBE ser thread-safe. 
 * 
 * @author Juan F. Codagnone
 * @since Feb 16, 2010
 */
public interface FetchQueue {

    /** 
     * incorpora un nuevo trabajo a la cola de fetching.
     * 
     * @throws RejectedExecutionException Si no puede aceptar nuevas tareas 
     *   (porque se invoc� a {@link #shutdown()})  
     */
    void add(FetchJob fetchJob) throws RejectedExecutionException;
    
    /** 
     * obtiene una tarea; si no tiene nada para entregar bloquea. Nunca retorna null.
     * Si fue interrumpido recibir�
     */
    FetchJob poll() throws InterruptedException;
    
    /** @return <code>true</code> si la cola est� vac�a */
    boolean isEmpty();
    
    /** shutdown */
    void shutdown();
    
    /**
     * return <code>true</code> si se esta apagando. 
     * No se aceptaran nuevas tareas
     */
    boolean isShutdown();
}
