/**
 * 
 *  Copyright (C) 2007 Enterprise Distributed Technologies Ltd
 *
 *  www.enterprisedt.com
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Bug fixes, suggestions and comments should be should posted on 
 *  http://www.enterprisedt.com/forums/index.php
 *
 *  Change Log:
 *
 *    $Log: FileTransferInputStream.java,v $
 *    Revision 1.1  2007-12-18 07:52:06  bruceb
 *    2.0 changes
 *
 *
 */
package com.enterprisedt.net.ftp;

import java.io.InputStream;

/**
 *  Super class of all input streams supported
 *
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
abstract public class FileTransferInputStream extends InputStream {

    /** 
     * Name of remote file being transferred
     */
    protected String remoteFile;
    
    /**
     * Has the stream been closed?
     */
    protected boolean closed = false;

    /**
     * Get the name of the remote file 
     * 
     * @return remote filename
     */
    public String getRemoteFile() {
        return remoteFile;
    }
    
}
