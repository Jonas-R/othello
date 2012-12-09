def convert(input, output):
	input = open(input, 'r')
	output = open(output, 'w')

	for line in input.readlines():
		output.write(convertLine(line))
	input.close()
	output.close()

def convertLine(line):
	out = ''
	
	i = 0
	while True:
		if line[i] == '+':
			out = out + 'b '
		elif line[i] == '-':
			out = out + 'w '
		elif line[i] == ':':
			out = out + line[i+2:i+5] + '\n'
			return out
		else:
			out = out + str((((int(line[i+1])-1) * 8) + (ord(line[i]) - 97)))
			out = out + '\n'
			i = i + 1
		i = i + 1
convert('xxx.gam', 'book.txt')