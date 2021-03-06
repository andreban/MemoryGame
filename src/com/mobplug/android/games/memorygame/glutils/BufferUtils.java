package com.mobplug.android.games.memorygame.glutils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class BufferUtils {
	public static FloatBuffer createFloatBuffer(int size) {
		ByteBuffer bf = ByteBuffer.allocateDirect(size * 4);
		return bf.order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	public static ShortBuffer createShortBuffer(int size) {
		ByteBuffer bf = ByteBuffer.allocateDirect(size * 2);
		return bf.order(ByteOrder.nativeOrder()).asShortBuffer();
	}
	public static IntBuffer createIntBuffer(int size) {
		ByteBuffer bf = ByteBuffer.allocateDirect(size * 4);
		return bf.order(ByteOrder.nativeOrder()).asIntBuffer();
	}	
	
	public static ByteBuffer createByteBuffer(int size) {
		ByteBuffer bf = ByteBuffer.allocateDirect(size);
		return bf.order(ByteOrder.nativeOrder());
	}
}
