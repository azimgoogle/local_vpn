/*
 *  Copyright 2014 AT&T
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.w3engineers.vpn.packet;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * write packet data back to VPN client stream. This class is thread safe.
 */
public class ClientPacketWriterImpl implements IClientPacketWriter {
	private FileOutputStream clientWriter;

	public ClientPacketWriterImpl(FileOutputStream clientWriter){
		this.clientWriter = clientWriter;
	}

	@Override
	public synchronized void write(byte[] data) throws IOException {
		if(data != null && data.length > 0) {
			clientWriter.write(data);
		}
	}

	@Override
	public synchronized void write(byte[] data, int offset, int count) throws IOException {
		clientWriter.write(data, offset, count);
	}
}
