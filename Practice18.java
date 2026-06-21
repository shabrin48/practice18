import java.util.Arrays;
import java.util.Scanner;

class MyArrayListData<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;

    public MyArrayListData() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public MyArrayListData(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Місткість буфера не може бути меншою за 0: " + initialCapacity);
        }
        this.elementData = new Object[initialCapacity];
        this.size = 0;
    }

    public void add(T element) {
        ensureCapacity();
        elementData[size++] = element;
    }

    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Некоректний індекс для додавання: " + index + ", Поточний розмір: " + size);
        }
        ensureCapacity();
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T oldValue = (T) elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;

        return oldValue;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elementData[index];
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return elementData.length;
    }

    private void ensureCapacity() {
        if (size == elementData.length) {
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity > 0 ? oldCapacity + (oldCapacity >> 1) : DEFAULT_CAPACITY;
            elementData = Arrays.copyOf(elementData, newCapacity);
            System.out.println("\n[Система: Буфер автоматично збільшено з " + oldCapacity + " до " + newCapacity + "]\n");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Передано помилковий аргумент! Індекс: " + index + ", Розмір списку: " + size);
        }
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elementData[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
public class Practice18 {
    public static void main(String[] args) {
        MyArrayListData<String> arrayList = new MyArrayListData<>(3);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Демонстрація роботи Переліку Масивів (Array List) ===");

        while (true) {
            System.out.println("\nПоточний Array List: " + arrayList);
            System.out.println("Кількість елементів (Size): " + arrayList.getSize() + " | Елементів в буфері (Capacity): " + arrayList.getCapacity());
            System.out.println("1. Додати елемент в кінець");
            System.out.println("2. Додати елемент за індексом");
            System.out.println("3. Видалити елемент за індексом");
            System.out.println("4. Отримати елемент за індексом");
            System.out.println("5. Вийти з програми");
            System.out.print("Оберіть дію (1-5): ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Введіть значення елемента: ");
                        String element = scanner.nextLine();
                        arrayList.add(element);
                        System.out.println("Елемент успішно додано.");
                        break;
                    case "2":
                        System.out.print("Введіть індекс: ");
                        int addIdx = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть значення елемента: ");
                        String addEl = scanner.nextLine();
                        arrayList.add(addIdx, addEl);
                        System.out.println("Елемент додано на позицію " + addIdx);
                        break;
                    case "3":
                        System.out.print("Введіть індекс для видалення: ");
                        int remIdx = Integer.parseInt(scanner.nextLine());
                        String removed = arrayList.remove(remIdx);
                        System.out.println("Успішно видалено елемент: " + removed);
                        break;
                    case "4":
                        System.out.print("Введіть індекс для отримання: ");
                        int getIdx = Integer.parseInt(scanner.nextLine());
                        System.out.println("Знайдено елемент: " + arrayList.get(getIdx));
                        break;
                    case "5":
                        System.out.println("Програму завершено. Дякуємо!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Помилка: Невідома дія. Оберіть пункт від 1 до 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка введення: Для роботи з індексами потрібно вводити лише цілі числа!");
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("Обробка помилкового аргументу: " + e.getMessage());
            }
        }
    }
}