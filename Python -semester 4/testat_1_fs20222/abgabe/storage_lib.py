# -*- coding: utf-8 -*-


import pathlib


class Storage:
    ''' This is the Storage class.
    '''

    def __init__(self, storage_path):
        '''Creates a Storage object.

        Arguments:
            storage_path  --  The path to the storage folder
        '''
        self.__storage_path = pathlib.Path(storage_path)
        self.__storage_data = {}

        # for each text file in the given folder
        for file in self.__storage_path.glob('*.txt'):
            with open(file, encoding='utf-8') as f:
                attributes = {}
                for line in f:
                    # try to extract the attribute name and value
                    kw = line.strip().split('=', maxsplit=1)
                    if len(kw) == 2:
                        # keep the valid attribute
                        attributes[kw[0].lower()] = kw[1]

            # store the attributes for that part number
            self.__storage_data[file.stem.upper()] = attributes

    def module_info(self, article_number):
        '''Returns the information dictionary of a given module.

        Arguments:
            article_number  --  Article number of the desired module
        '''

        return self.__storage_data.get(article_number.upper())

    def take_module(self, article_number, count):
        '''Returns a certain module from the storage

        Arguments:
            article_number  --  Article number of the desired module
            count           --  Number of modules to be taken from storage
        '''
        # get the number of that specific article in the storage
        article = self.__storage_data.get(article_number.upper())
        if article is not None and 'count' in article:
            in_storage = int(article['count'])
        else:
            return None

        if(in_storage < count):
            return None

        # update the count in the storage
        new_count = in_storage - count
        article['count'] = str(new_count)

        # notify the availability
        return count, article_number


# --- Test --------------------------------------------------------------------
if __name__ == '__main__':
    s = Storage('storage/')
    print(s.module_info('CO304'))
    print(s.module_info('xyz'))
    print(s.take_module('CO304', 2))
    print(s.take_module('CO304', 2000))
