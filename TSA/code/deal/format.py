from nltk.tokenize import word_tokenize
# tokenize 语句分割，将一整句自然语言分割为多个单词
from nltk.stem import WordNetLemmatizer
# lemmatizer 词形还原，将英语单词还原到一般形式

import pickle
from collections import OrderedDict

org_train_file = '../../data/raw/training.1600000.processed.noemoticon.csv'
org_test_file = '../../data/raw/testdata.manual.2009.06.14.csv'


# 提取文件中有用的字段
def useful_filed(org_file, output_file):
    output = open(output_file, 'w')
    with open(org_file, buffering=10000, encoding='latin-1') as f:
        try:
            for line in f:
                '''
                处理前格式：
                积极 "4"
                tweetID "2193601966"
                时间 "Tue Jun 16 08:40:49 PDT 2009"
                Query "NO_QUERY"
                用户 "AmandaMarie1028"
                内容 "Just woke up. Having no school is the best feeling ever"
                '''

                '''
                处理后格式：
                [0, 0, 1]:%:%:%: that's a bummer. You should got David Carr of Third Day to do it. ;D
                '''
                line = line.replace('"', '')
                clf = line.split(',')[0]  # 4
                if clf == '0':
                    clf = [0, 0, 1]  # 消极评论
                elif clf == '2':
                    clf = [0, 1, 0]  # 中性评论
                elif clf == '4':
                    clf = [1, 0, 0]  # 积极评论

                tweet = line.split(',')[-1]
                outputline = str(clf) + ':%:%:%:' + tweet
                output.write(outputline)
        except Exception as e:
            print(e)
    output.close()  # 处理完成，处理后文件大小127.5M


useful_filed(org_train_file, '../../data/training.csv')
print("train.csv done.")
useful_filed(org_test_file, '../../data/testing.csv')
print("test.csv done.")


# 创建词汇表
def create_lexicon(train_file):
    lex = []
    lemmatizer = WordNetLemmatizer()
    with open(train_file, buffering=10000, encoding='latin-1') as f:
        try:
            count_word = {}  # 统计单词出现次数
            for line in f:
                tweet = line.split(':%:%:%:')[1]
                words = word_tokenize(tweet.lower())
                for word in words:
                    word = lemmatizer.lemmatize(word)
                    if word not in count_word:
                        count_word[word] = 1
                    else:
                        count_word[word] += 1

            # 根据单次出现次数排序
            count_word = OrderedDict(sorted(count_word.items(), key=lambda t: t[1]))

            for word in count_word:
                # 过滤掉出现太少或出现太多的单词
                if 10000 > count_word[word] > 100:
                    lex.append(word)
        except Exception as e:
            print(e)
        print(lex)
    return lex
# lex保存了所有评论中出现频率居中的单词，是一个字符串数组


lex = create_lexicon('../../data/training.csv')

# lex被保存在pickle文本文件中
with open('../../data/lexcion.pickle', 'wb') as f:
    pickle.dump(lex, f)

print("lex done.")

"""
# 把字符串转为向量
    def string_to_vector(input_file, output_file, lex):
        output_f = open(output_file, 'w')
        lemmatizer = WordNetLemmatizer()
        with open(input_file, buffering=10000, encoding='latin-1') as f:
            for line in f:
                label = line.split(':%:%:%:')[0]
                tweet = line.split(':%:%:%:')[1]
                words = word_tokenize(tweet.lower())
                words = [lemmatizer.lemmatize(word) for word in words]
    
                features = np.zeros(len(lex))
                for word in words:
                    if word in lex:
                        features[lex.index(word)] = 1  # 一个句子中某个词可能出现两次,可以用+=1，其实区别不大
    
                features = list(features)
                output_f.write(str(label) + ":" + str(features) + '\n')
        output_f.close()


f = open('../../data/lexcion.pickle', 'rb')
lex = pickle.load(f)
f.close()

# lexcion词汇表大小112k,training.vec大约112k*1600000  170G  太大，只能边转边训练了
# string_to_vector('training.csv', 'training.vec', lex)
# string_to_vector('tesing.csv', 'tesing.vec', lex)
"""
