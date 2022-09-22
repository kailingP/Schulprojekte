# -*- coding: utf-8 -*-
import csv
import pathlib
from storage_lib import Storage

# --- Implementierung Testat --------------------------------------------------
# Implementieren Sie hier die beiden Klassen ModuleBuilder
# und MissingModuleError.


class ModuleBuilder:
    """
    retrieve useful info from order_file and storage folder
    """
    def __init__(self, order_file, storage_path):
        """
        ModuleBuilder takes two file paths as protected variables
        and set the instance variables
        """
        self._order_data = dict()
        self._units = int()
        self._order = pathlib.Path(order_file)
        self._storage = Storage(storage_path)

        with open(self._order, encoding='utf-8') as f:
            unit_info = f.readline()
            self._units = int(unit_info.split(";")[1])
            f.readline()
            data = csv.reader(f, delimiter=';')

            for line in data:
                if line[1] not in self._order_data.keys():
                    value_list = list()
                    value_list.append(line[0])
                    self._order_data.update({line[1]: value_list})
                else:
                    self._order_data[line[1]].append(line[0])

    def __str__(self):
        """
        overwrite default str method and return a nicely formatted string
        """
        new_str = ''
        sorted_order = sorted(self._order_data.items(), key=lambda x: x[0])
        str_line = [["Article Number", "Quantity", "References"]]
        for item in sorted_order:
            item = list(item)
            item.insert(1, len(item[1]))
            str_line.append(item)

        col_one = max([len(item[0]) for item in str_line]) + 4
        col_two = max([len(str(item[1])) for item in str_line]) + 4
        col_three = len("References")
        sub_len = 0
        for item in str_line:
            sub_len = 0
            for sub_item in item[2]:
                sub_len += len(sub_item)
            sub_len += (len(item[2]) - 1)
        if sub_len > col_three:
            col_three = sub_len

        str_line.insert(1, ["-" * col_one, "-" * col_two, "-" * col_three])
        for item in str_line:
            if isinstance(item[2], list):
                reference_list = ','.join([x for x in item[2]])
                text = f"{item[0]:<{col_one}}" + f"{item[1]:<{col_two}}" \
                       + f"{reference_list}" + "\n"
            else:
                text = f"{item[0]:<{col_one}}" + f"{item[1]:<{col_two}}" \
                       + f"{item[2]:{col_three}}" + "\n"
            new_str += text
        return new_str

    def availability(self, units=None):
        """
        check article availability in storage and return the info as a dict()
        """
        article_keys = list(self._order_data.keys())
        article_values, article_availability, missing_units = [], [], []
        counts = [len(articles) for articles in self._order_data.values()]
        if units and units > 0:
            counts = [i * units for i in counts]
        elif units and units <= 0:
            raise ValueError('The given value for units '
                             'must be greater than 0.')
        else:
            counts = [i * self._units for i in counts]

        for i, key in enumerate(article_keys):
            stock = 0
            article = self._storage.module_info(key)
            if article:
                stock = int(article['count'])
            article_availability.append(stock)

            if stock - counts[i] >= 0:
                missing_units.append(0)
            else:
                missing_units.append(counts[i] - stock)

        for i in range(len(article_keys)):
            article_value_entry = {'available': article_availability[i],
                                   'required': counts[i],
                                   'missing': missing_units[i]}
            article_values.append(article_value_entry)

        availability_dic = dict(zip(article_keys, article_values))
        return availability_dic

    def build(self):
        """
        list out missing needed articles in Error message
        if all available, take article from storage
        and return total ordered quantity
        """
        module_dic = self.availability()
        missing_modules = []
        for key, value in module_dic.items():
            if value['missing']:
                missing_modules.append(key)
        if missing_modules:
            raise MissingModuleError(missing_modules)
        else:
            total_count = 0
            for key, value in module_dic.items():
                total_count += self._storage.take_module(key,
                                                         value['required'])
            return total_count


class MissingModuleError(Exception):
    """
    an exception with customized error message
    """
    def __init__(self, modules):
        self.modules = tuple(modules)
        super().__init__(f"The following modules are missing from storage: "
                         f"{','.join(modules)}")


# --- Testcode ----------------------------------------------------------------
# Fügen Sie hier Ihren Testcode ein. Nutzen Sie dafür unter anderem die
# Code-Beispiele in den grauen Boxen der Aufgabenstellung.
if __name__ == '__main__':
    s = Storage("storage")
    mb = ModuleBuilder("orders/produktionsliste_1.csv", "storage")

    print(mb)
    mb2 = ModuleBuilder("orders/produktionsliste_2.csv", "storage")
    print(mb2.build())
    print(mb.availability(20))
    print(mb.availability())
    # raise MissingModuleError(["TF1902", "HL20D", "P394NF"])

    print(mb.availability(-10))
    mb.availability()
    mb2.availability()

    print(mb2.build())
    # print(mb._units)
    print(s.module_info("CO304"))
    # print(type(mb._storage))
    mb3 = ModuleBuilder("orders/produktionsliste_3.csv", "storage")
    print(mb3.build())

    # print(mb._units)
    # print(mb._storage)
    print(s.module_info("CO304"))
    print(s.module_info("xyz"))
    print(s.take_module("CO304", 2))
    print(s.take_module("CO304", 2000))
