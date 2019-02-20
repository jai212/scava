package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamMetadata implements Serializable {

	public class Stream {
		private String name;
		private long size;
		private long inFlight;
		private boolean isTopic;
		private long numberOfSubscribers;

		public Stream(String name, long size, long inFlight, boolean isTopic, long numberOfSubscribers) {
			this.name = name;
			this.size = size;
			this.inFlight = inFlight;
			this.isTopic = isTopic;
			this.numberOfSubscribers = numberOfSubscribers;
		};

		public String getName() {
			return name;
		}

		public long getSize() {
			return size;
		}

		public long getInFlight() {
			return inFlight;
		}

		public boolean isTopic() {
			return isTopic;
		}

		public long getNumberOfSubscribers() {
			return numberOfSubscribers;
		}

		public void setNumberOfSubscribers(int numberOfSubscribers) {
			this.numberOfSubscribers = numberOfSubscribers;
		}
	}

	private Set<Stream> streams = new HashSet<Stream>();

	//

	public boolean addStream(String name, long size, long inFlight, boolean isTopic, long l) {
		return streams.add(new Stream(name, size, inFlight, isTopic, l));
	}

	public Set<Stream> getStreams() {
		return streams;
	}

	public Stream getStream(String name) {
		try {
			return streams.stream().filter(s -> s.name.equals(name)).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public void pruneNames(int length) {
		for (Stream s : streams)
			if (s.name.length() >= length)
				s.name = s.name.substring(0, length);
			else if (s.name.length() < length)
				s.name = String.format("%-" + length + "s", s.name);
	}

	@Override
	public String toString() {
		String ret = "Stream Metadata at epoch: " + System.currentTimeMillis() + "\r\n";
		for (Stream s : streams)
			ret = ret + s.name + "\tsize: " + s.size + "\tinFlight: " + s.inFlight + "\tisTopic: " + s.isTopic
					+ "\tnumberOfSubscribers: " + s.numberOfSubscribers + "\r\n";
		return ret;
	}
}