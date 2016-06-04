package com.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 排序：
 * 内部排序：只是用内存
 * 	插入排序：
 * 		直接插入排序
 * 		希尔排序
 * 	选择排序
 * 		简单选择排序
 * 		堆排序
 * 	交换排序
 * 		冒泡排序
 * 		快速排序
 * 	归并排序
 * 	基数排序
 * 外部排序：内存和外存结合使用
 * @author GavinCee
 *
 */
public class SortUtil {
	
	public static void main(String[] args) {
		int[] arr = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56,
				17, 18, 23, 34, 15, 35, 25, 53, 51};
		insertSort(arr);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56,
				17, 18, 23, 34, 15, 35, 25, 53, 51};
		hillSort(arr);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56,
				17, 18, 23, 34, 15, 35, 25, 53, 51};
		simpleSelectSort(arr);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56,
				17, 18, 23, 34, 15, 35, 25, 53, 51};
		heapSort(arr);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56,
				17, 18, 23, 34, 15, 35, 25, 53, 51};
		bubbleSort(arr);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56,
				17, 18, 23, 34, 15, 35, 25, 53, 51};
		quickSort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56,
				17, 18, 23, 34, 15, 35, 25, 53, 51};
		mergeSort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
		
		arr = new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56,
				17, 18, 23, 34, 15, 35, 25, 53, 51};
		radixSort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	/**
	 * 基数排序：将所有带比较数值统一为同样的数位长度，数位较短的数前面补0，然后从最低位开始，依次进行
	 * 排序，这样从最低位排序一直到最高位排序完成后数组就变成一个有序的了
	 * @param arr
	 */
	public static void radixSort(int[] arr) {
		//找到最大数
		int max = arr[0];
		for(int i = 1; i < arr.length; i++) {
			if(arr[i] > max) {
				max = arr[i];
			}
		}
		//求出位数
		int time = 0;
		while(max > 0) {
			max = max / 10;
			time++;
		}
		
		//建立10个队列
		List<ArrayList<Integer>> queue = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			ArrayList<Integer> queue1 = new ArrayList<>();
			queue.add(queue1);
		}
		
		//进行time次分配
		for(int i = 0; i < time; i++) {
			//分配数组元素
			for(int j = 0; j < arr.length; j++) {
				int x = arr[j] % (int)Math.pow(10, i + 1) / (int)Math.pow(10, i);
				ArrayList<Integer> queue2 = queue.get(x);
				queue2.add(arr[j]);
				queue.set(x, queue2);
			}
		}
		
		int count = 0;
		for(int k = 0; k < 10; k++) {
			while(queue.get(k).size() > 0) {
				ArrayList<Integer> queue3 = queue.get(k);
				arr[count] = queue3.get(0);
				queue3.remove(0);
				count++;
			}
		}
	}
	
	/**
	 * 归并排序：把两个或两个以上有序表合并成一个新的有序表，即把待排序数组分为若干个子序列，每个子序列
	 * 是有序的，然后再把子序列合并为整体
	 * @param arr
	 */
	public static void mergeSort(int[] arr, int left, int right) {
		if(left < right) {
			//找出中间索引
			int center = (left + right) / 2;
			//对左边数组进行递归
			mergeSort(arr, left, center);
			//对右边数组进行递归
			mergeSort(arr, center + 1, right);
			//合并
			merge(arr, left, center, right);
		}
	}
	
	private static void merge(int[] data, int left, int center, int right) {
		int[] tempArr = new int[data.length];
		int mid = center + 1;
		int third = left;
		int tmp = left;
		while (left <= center && mid <= right) {
			//从两个数组中取出最小的
			if(data[left] <= data[mid]) {
				tempArr[third++] = data[left++];
			} else {
				tempArr[third++] = data[mid++];
			}
		}
		while(mid <= right) {
			tempArr[third++] = data[mid++];
		}
		while(left <= center) {
			tempArr[third++] = data[left++];
		}
		//将中间数组拷贝回原数组
		while(tmp <= right) {
			data[tmp] = tempArr[tmp++];
		}
	}
	
	/**
	 * 快速排序：选择一个基准元素，通常选择第一个，通过一趟扫描，将待排序部分分成两部分，一部分比基准元素小
	 * 一部分大于等于基准元素，此时，基准元素确定好位置，然后使用同样方式递归排序两部分
	 * @param arr
	 */
	public static void quickSort(int[] arr, int low, int high) {
		if(low < high) {
			int middle = getMiddle(arr, low, high);//对数组一分为二
			//对低数组进行递归排序
			quickSort(arr, low, middle - 1);
			//对高数组进行递归排序
			quickSort(arr, middle + 1, high);
		}
	}
	
	private static int getMiddle(int[] arr, int low, int high) {
		int temp = arr[low];
		while(low < high) {
			while(low < high && arr[high] >= temp) {
				high--;
			}
			arr[low] = arr[high];//比中轴小的数移到低端
			while(low < high && arr[low] <= temp) {
				low ++;
			}
			arr[high] = arr[low];//比中轴大的数移到高端
		}
		arr[low] =  temp;
		return low;
	}
	
	/**
	 * 冒泡排序：在要排序的一组数中，在对当前还未排好序的范围内的全部数字，自上而下对相邻的两个数依次进行
	 * 比较和调整，让较大的数往下沉，较小的数往上浮。
	 * @param arr
	 */
	public static void bubbleSort(int[] arr) {
		for(int i = 0; i < arr.length - 1; i++) {
			for(int j = 0; j < arr.length - 1 - i; j++) {
				if(arr[j] > arr[j + 1]) {
					swap(arr, j, j + 1);
				}
			}
		}
	}
	
	/**
	 * 堆排序：即二叉树排序，首先需要建立二叉树，然后进行二叉树排序
	 * @param arr
	 */
	public static void heapSort(int[] arr) {
		int arrlength = arr.length;
		for(int i = 0; i< arrlength; i++) {
			//建堆
			buildHeap(arr, arrlength - 1 -i);
			//交换堆顶和最后一个元素
			swap(arr, 0, arrlength - 1 - i);
		}
	}
	
	public static void swap(int[] data, int i, int j) {
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	private static void buildHeap(int[] data, int lastIndex) {
		//从最后一个节点的父节点开始
		for(int i = (lastIndex - 1) / 2; i >= 0; i--) {
			//保存当前节点
			int k = i;
			//如果当前节点的子节点存在
			while(k * 2 + 1 <= lastIndex) {
				//当前节点的左子节点
				int biggerIndex = 2 * k + 1;
				if(biggerIndex < lastIndex) {
					if(data[biggerIndex] < data[biggerIndex + 1]) {
						biggerIndex ++;
					}
				}
				if(data[k] < data[biggerIndex]) {
					swap(data, k, biggerIndex);
					k = biggerIndex;
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * 直接插入排序：在要排序的一组数中，假设前面（n-1)[n >= 2]个数已经是排序好的，现在要把
	 * 第n个数插到前面的有序数中，使得这n个数也是排好序的，直至全部排好顺序
	 * @param arr
	 */
	public static void insertSort(int[] arr) {
		int temp = 0;
		for(int i = 1; i < arr.length; i++) {
			int j = i - 1;
			temp = arr[i];
			for(;j >=0 && temp < arr[j]; j--) {
				arr[j + 1] = arr[j];
			}
			arr[j + 1] = temp;
		}
	}
	
	/**
	 * 希尔排序（最小增量排序）：算法先将数组按某个增量d（n/2）分成若干组，每组中记录的下标相差d。
	 * 对每组中全部元素进行直接插入排序，然后再用一个较小的增量(d/2)对它进行分组，在每组中再进行
	 * 直接插入排序，当增量减到1时，进行直接插入排序后，排序完成
	 * @param arr
	 */
	public static void hillSort(int[] arr) {
		int d = arr.length;
		int temp = 0;
		while(true) {
			d = (int) Math.ceil(d / 2);
			for(int x = 0; x < d; x++) {
				for(int i = x + d; i < arr.length; i += d) {
					int j = i -d;
					 temp = arr[i];
					 for(;j >= 0 && temp < arr[j]; j -= d) {
						 arr[j+d] = arr[j];
					 }
					 arr[j+d] = temp;
				}
			}
			if(d == 1) {
				break;
			}
		}
	}

	/**
	 * 简单选择排序：再要排序的一组数字钟，选出最小的一个数与第一个位置交换，然后在剩下的数当中再找最小的
	 * 与第二个数交换，如此循环到最后一个数为止
	 * @param arr
	 */
	public static void simpleSelectSort(int[] arr) {
		int position = 0;
		for(int i = 0; i < arr.length; i++) {
			int j = i+ 1;
			position = i;
			int temp = arr[i];
			for(; j<arr.length; j++) {
				if(arr[j] < temp) {
					temp = arr[j];
					position = j;
				}
			}
			arr[position] = arr[i];
			arr[i] = temp;
		}
	}
	
}
