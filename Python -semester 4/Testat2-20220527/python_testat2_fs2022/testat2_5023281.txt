# -*- coding: utf-8 -*-
import re
import numpy as np

# --- Implementierung Testat --------------------------------------------------
# Implementieren Sie hierdie Lösung für die zweite Testataufgabe.


def netlist_parser(fname):
    '''
    Parameters
    ----------
    fname : a text file

    Returns
    -------
    data_dic : TYPE
        ignore invalid data if the first element is not about 'R/V/I'
        each valid row should be a dictinary when
        first row element as key
        the rest elements are interpreted accordingly as value
        ignore optional 'DC' input
        convert value with units to float numbers
    '''
    with open(fname) as f:
        data = f.readlines()
        data_dic = dict()

        for ele in data:
            if (ele.lstrip().startswith('*') or ele.lstrip().upper()[0]
                    not in ['R', 'V', 'I']):
                continue
            data = ele.split(';')[0]
            ele = list(data.strip().split(' '))

            ele_key = ele[0].title()

            unit = ele[3]
            if unit.upper() == 'DC':
                unit = ele[4]

            if unit[-1].isalpha():
                unit = convert(unit)
            else:
                unit = float(unit)

            ele_value = {'n+': f'Vn{ele[1]}',
                         'n-': f'Vn{ele[2]}',
                         'value': unit}
            data_dic[ele_key] = ele_value
        return data_dic


def inventory(elements):
    '''
    group elements by categories and list the items per category out
    '''
    resistor, voltage, source = list(), list(), list()
    for key in elements.keys():
        if key.upper().startswith('R'):
            resistor.append(key)
        elif key.upper().startswith('V'):
            voltage.append(key)
        elif key.upper().startswith('I'):
            source.append(key)
    result = 'Resistors: ' + ', '.join(resistor) + '\n' + 'Voltage Sources: '\
        + ', '.join(voltage) + '\n' + 'Current Sources: ' + ', '.join(source)
    return result


def convert(unit_input):
    '''
    Parameters
    ----------
    unit_input : a string which can't be convert to float directly


    Returns
    -------
    a float number
        convert a num with prefix case incensitively to a valid float number
    '''
    a = re.match('[0.-9]+', unit_input)
    num = a.group(0)
    prefix = unit_input[len(num):].upper()
    unit_convert = {'F': 1e-15, 'P': 1e-12, 'N': 1e-9,
                    'U': 1e-6, 'M': 1e-3, 'K': 10**3,
                    'MEG': 10**6, 'G': 10**9, 'T': 10**12}

    return float(num) * (unit_convert.get(prefix.upper()))


def mna_build(elements):
    '''
    take elements input and build the matrix for solving the math formula

    Parameters
    ----------
    elements : dictionary

    Returns
    -------
    unknowns : array
        add all unknowns needed
    matrix : matrix
        based on math formula
    y : array
        result
    '''
    vs = 0
    vn_list = list()
    for k in elements.keys():
        if k[0].upper() == 'V':
            vs += 1
        vn_list.append(elements[k]['n+'])
        vn_list.append(elements[k]['n-'])
    g_matrix_size = len(set(vn_list)) - 1

    matrix = np.zeros((g_matrix_size + vs, g_matrix_size + vs))
    y = np.zeros(g_matrix_size + vs)
    unknowns = [f'Vn{index+1}' for index in range(g_matrix_size)]
    counter = g_matrix_size
    for k, v in elements.items():
        n_plus = int(v['n+'][2:]) - 1
        n_minus = int(v['n-'][2:]) - 1
        if k[0].upper() == 'R':
            if n_minus > -1 and n_plus > -1:
                matrix[n_plus][n_plus] += 1/v['value']
                matrix[n_plus][n_minus] -= 1/v['value']
                matrix[n_minus][n_plus] -= 1/v['value']
                matrix[n_minus][n_minus] += 1/v['value']
            elif n_minus == -1:
                matrix[n_plus][n_plus] += 1/v['value']
            elif n_plus == -1:
                matrix[n_minus][n_minus] += 1/v['value']

        if k[0].upper() == 'V':
            unknowns.append(f'I{k.lower()}')
            if n_plus > -1:
                matrix[counter][n_plus] += 1
                matrix[n_plus][counter] += 1

            if n_minus > -1:
                matrix[counter][n_minus] -= 1
                matrix[n_minus][counter] -= 1
            y[counter] += v['value']
            counter += 1
        if k[0].upper() == 'I':
            if n_plus > -1:
                y[n_plus] -= v['value']
            if n_minus > -1:
                y[n_minus] += v['value']

    return unknowns, matrix, y


def mna_solve(unknowns, M, y):
    '''
    all input are elements to build a math function
    solve the funtion and
    return the result as a dict() with variable names as keys
    '''
    solve_result = np.linalg.solve(M, y)
    return dict(zip(unknowns, solve_result))


def mna_report(elements, solution):
    '''
    calculate voltage and the current of each element
    according to the math formula
    '''
    report_dict = dict.fromkeys(elements.keys())
    for key in elements.keys():
        value_dict = dict()
        if key.upper().startswith('R'):
            value_dict['V'] = solution.get(
                elements[key]['n+'], 0) - solution.get(elements[key]['n-'], 0)
            value_dict['I'] = value_dict['V']/elements[key]['value']

        elif key.upper().startswith('V'):
            value_dict['V'] = elements[key]['value']
            value_dict['I'] = solution.get(f'I{key.lower()}', 0)

        elif key.upper().startswith('I'):
            value_dict['V'] = solution.get(
                elements[key]['n+'], 0) - solution.get(elements[key]['n-'], 0)
            value_dict['I'] = elements[key]['value']
        report_dict[key] = value_dict
    return report_dict


# --- Testcode ----------------------------------------------------------------
# Fügen Sie hier Ihren Testcode ein. Nutzen Sie dafür unter anderem die
# Code-Beispiele in den grauen Boxen der Aufgabenstellung sowie auch die
# Angaben in den *.results Files im abgegebenen Ordner.
if __name__ == '__main__':
    elements = netlist_parser("netlist2.cir")
    print(elements)
    s = inventory(elements)
    print(s)
    unknowns, m, y = mna_build(elements)
    print(unknowns)
    solution = mna_solve(unknowns, m, y)
    print(solution)

    print(m.tolist())

    print(mna_report(elements, solution))
