package neural_network;

import jeigen.DenseMatrix;

public class NeuralNetwork_Params {
	public static DenseMatrix H1_5 = new DenseMatrix(new double[][] 
			{{1.20393, 0.03535, -0.37911, 0.49384, 
				  1.16336, -1.24633, -1.41033, -1.40284, -0.21966, 
				  0.08617, -1.33924, -1.73544, -1.2261, 
				  0.91768, -0.71096, -1.02585, -1.29847, -1.08159, 
				  0.22775, -0.95578, -0.8263, -1.13248, -1.17454, 0.18906, -0.60082, 
				  1.29477}, {0.32008, 
				  0.14104, -0.61845, -0.23737, -0.04834, -0.43008, -0.15706, 
				-0.73231, -0.6547, 0.10024, -0.42324, -0.41109, 
				  0.28647, -0.3141, -0.43934, 0.35341, -0.28992, 
				  0.17748, -0.15922, -0.45865, 0.3892, 0.43324, 0.3324, 
				  0.36615, -0.05716, 0.26552}, {0.15789, 
				  0.43574, -0.42844, -0.53339, -0.07516, -0.28645, -0.02747, 
				-0.62997, -0.0324, -0.28657, -0.5789, -0.17506, -0.00156, -0.36216, 
				-0.59428, -0.50835, 0.32993, -0.2529, 0.06477, -0.52219, -0.33785, 
				  0.07357, -0.01874, 0.22622, -0.42949, 0.57064}, {0.76047, 
				  0.13161, -0.45748, 0.16055, 
				  0.4861, -0.6531, -0.3742, -0.7557, -0.34608, -0.06558, 
				  0.00118, -1.10116, -0.19018, -0.07532, 
				  0.22161, -0.93271, -0.97403, -0.81063, -0.05776, 0.11894, 
				  0.17067, -0.93661, -0.60219, 0.09099, -0.0553, 
				  1.16557}, {0.42029, -0.12499, -0.14626, -0.09741, -0.26596, 
				  0.51522, -0.09318, 0.45884, -0.46076, -0.16435, -0.35005, 
				  0.48564, -0.5195, 0.00805, 0.03244, 0.41489, 
				  0.34302, -0.10231, -0.31147, 0.13619, -0.21727, 0.10329, -0.34727, 
				  0.20941, 0.16392, -0.19163}, {-0.22888, 0.09773, 0.35685, -0.21888, 
				  0.11064, -0.21225, -0.65681, -0.04196, 0.06923, 
				  0.39788, -0.3026, -0.7324, -0.25093, 
				  0.24434, -0.66497, -0.28667, -0.28497, -0.67118, -0.33179, 
				-0.72482, -0.05968, -0.22127, -0.0012, 0.4827, 0.39124, 
				  0.00911}, {0.21495, -0.31501, 0.47743, 0.0423, 0.57125, 0.17171, 
				  0.24776, 0.22094, 
				  0.44225, -0.31914, -0.49682, -0.14567, -0.57031, -0.14487, 
				  0.23504, -0.39821, 0.13766, -0.16196, 0.43591, 0.2844, 
				  0.28611, -0.39152, 0.29016, -0.35654, -0.7101, 
				  0.23761}, {0.63478, -0.1779, 0.319, 0.3646, -0.11333, 
				  0.22878, -0.35588, -0.0038, 0.17031, 0.44919, 
				  0.21865, -0.17288, -0.5627, 0.17968, 
				  0.12615, -0.71131, -0.52492, -0.15918, 
				  0.07794, -0.01305, -0.39984, -0.4702, 0.16157, 0.36659, -0.68411, 
				  0.91807}, {0.70293, 0.01394, -0.03003, 0.7042, 
				  0.56654, -0.0296, -0.93507, -0.01825, 
				  0.09193, -0.15708, -0.54889, -0.49812, -0.94086, 
				  0.21684, -0.61593, -0.52774, -0.83053, -0.47271, 
				  0.39189, -0.00626, -0.62639, -1.128, -0.09019, -0.21668, -0.27075, 
				  0.324}, {0.29754, 0.20346, 0.16254, -0.12508, 
				  0.50227, -0.12732, -0.52593, 0.06481, 
				  0.03996, -0.34241, -0.18426, -0.46331, -0.1418, 
				  0.46844, -0.26452, -0.27619, -0.55754, 0.30089, 0.09277, -0.44488, 
				  0.22533, -0.16184, 0.04733, -0.22657, -0.085, -0.22043}, {0.92818, 
				  0.14312, 0.4388, 0.14998, 
				  0.61783, -0.98914, -0.42876, -0.01785, -0.24681, -0.35355, 
				-0.72547, -0.82912, -0.97946, 0.59409, 
				  0.13304, -0.72799, -0.2491, -0.71916, -0.2826, -0.70296, -0.13374, 
				-0.94244, -0.24279, 0.52277, -0.39398, 0.83095}, {-0.46489, 
				  0.0353, -0.49313, -0.11238, 0.21483, 0.48566, 
				  0.09978, -0.49454, -0.27517, 0.31989, -0.46141, 0.05136, 0.22394, 
				  0.30091, 0.12051, -0.05837, -0.00772, -0.44271, 0.45158, 
				  0.20408, -0.4668, 0.44572, 0.15468, -0.38472, 
				  0.32748, -0.48387}, {0.45453, 0.08154, -0.37598, 
				  0.66782, -0.03146, -0.17776, -0.91782, -0.44036, -0.39224, -0.12082,
				   0.02683, -0.75474, -0.79282, 
				  0.62393, -0.04684, -0.77715, -0.37903, -0.8374, -0.12798, -0.00621, 
				-0.10947, -0.60838, -0.18714, -0.35359, 0.10056, 0.12252}, {0.33072, 
				  0.58251, 0.06654, -0.04513, 0.26774, 0.09604, -1.07283, 
				  0.00276, -0.61058, -0.3585, -0.20659, -0.66834, -0.88341, -0.06825, 
				-0.36832, -0.60732, -0.70953, 0.05913, 
				  0.68773, -0.24481, -0.72641, -0.53631, -0.81596, 0.58823, 0.24753, 
				  0.8217}, {0.20209, 0.22821, -0.39088, -0.44833, -0.41196, -0.11489, 
				  0.10772, -0.72556, -0.54727, 0.38346, -0.2551, -0.53569, -0.61202, 
				  0.12603, -0.44735, 0.19559, -0.30226, 0.30271, 0.37072, 
				  0.48123, -0.26645, 0.32231, 0.40178, -0.1835, 0.16227, 
				  0.35382}, {-0.02184, -0.45624, -0.09458, -0.19814, -0.02527, 
				-0.33087, 0.29145, 0.44243, -0.33615, -0.46927, 
				  0.50707, -0.1823, -0.31997, -0.48578, 0.13997, 0.37901, -0.30878, 
				  0.43255, 0.41345, 0.2599, -0.42516, 0.48441, 0.45593, 
				  0.42383, -0.02378, -0.39273}, {0.04446, 0.01829, 0.54177, 0.53966, 
				  0.41197, -1.0857, -0.75301, -0.22352, 0.32484, 
				  0.30265, -0.8872, -0.91231, -1.16004, 0.57256, 
				  0.08275, -0.90842, -0.87021, -0.88234, -0.19577, -0.78179, 
				-0.58376, -0.7827, -0.79903, 0.12822, -0.07657, 0.48001}, {0.82863, 
				  0.53029, 0.4734, 0.95945, 
				  0.87838, -0.42359, -1.55774, -0.2356, -0.5856, -0.08115, -1.22783, 
				-1.61723, -0.70785, 0.51395, -0.46071, -0.74474, -0.85561, -0.6589, 
				  0.0607, -0.17126, -0.56685, -1.25081, -0.89105, 0.88995, -0.59527, 
				  1.77701}, {0.24967, -0.30877, -0.07744, -0.54496, -0.38439, 
				-0.25176, 0.24688, 0.26498, 0.4441, -0.51617, -0.33882, 
				  0.4445, -0.23464, 0.02262, -0.36698, 0.31081, -0.14874, 0.06923, 
				  0.38041, 0.4266, -0.19636, -0.22487, 0.13515, 0.09998, 0.24766, 
				  0.56829}, {-0.48944, 0.05712, 
				  0.00918, -0.24834, -0.36574, -0.30468, -0.08211, 0.04256, 
				  0.13949, -0.24675, -0.31156, 0.02708, -0.37095, -0.07225, -0.07055, 
				  0.3194, -0.35945, 0.45808, -0.25206, 0.12136, -0.05188, 
				  0.24474, -0.06911, 0.22179, 0.25945, -0.16904}, {-0.48805, -0.26166,
				   0.09412, 0.30582, -0.42557, 0.00047, 
				  0.29058, -0.46939, -0.29663, -0.2836, 0.38281, 0.35527, -0.00766, 
				  0.36977, -0.29684, -0.16184, 0.38625, 
				  0.16034, -0.41334, -0.38087, -0.15296, 0.26975, -0.14403, 0.07744, 
				  0.42308, 0.05742}, {0.91909, 0.34927, 0.72998, 0.81278, 
				  1.28723, -0.92202, -1.48033, -0.51739, -0.35764, 
				  0.09077, -0.45546, -1.60983, -1.0274, 
				  0.28062, -0.54705, -0.97663, -1.17927, -0.93961, 
				  0.50838, -0.75296, -0.89732, -1.11881, -1.21606, 0.42209, -0.3341, 
				  1.52771}, {0.4206, -0.30903, -0.16621, 0.04478, -0.33843, -0.30133, 
				  0.18497, -0.06169, 0.3849, -0.32792, -0.40622, 0.05656, 0.4957, 
				  0.11718, -0.47366, 0.09076, -0.26532, 0.08803, -0.34272, 0.01605, 
				  0.41942, 0.10373, 0.27707, 0.29073, 0.06806, -0.14762}, {0.65144, 
				  0.70306, 0.00455, 0.68289, 
				  0.76399, -0.35081, -0.45352, -0.97595, -0.29898, 
				  0.04251, -0.98812, -0.29143, -1.00434, 
				  0.01809, -0.62023, -0.23385, -1.04357, -0.9397, -0.19663, -0.8946, 
				-0.62312, -0.38186, -0.60552, -0.04555, 0.0839, 
				  0.57925}, {-0.15494, -0.20608, 0.47177, 0.57508, 
				  0.72604, -0.13667, -0.65383, -0.07059, 0.01311, 
				  0.14034, -0.82298, -0.83281, -0.50478, -0.26987, 
				  0.39984, -0.97743, -1.02731, -0.12441, -0.31843, -0.05082, 
				-0.39012, -0.12704, -0.38943, -0.07993, 0.06731, 0.28088}}
	);
	
	public static DenseMatrix H2_5 = new DenseMatrix(new double[][] 
			{{0.40079, -0.30325, 0.29196, 0.28123, -0.01891, -0.26446, 0.21427, 
				  0.34132, -0.14149, -0.31779, -0.03622, -0.16502, 
				  0.46358, -0.17742, -0.14482, 0.50203, 0.39225, 0.1669, 0.51715, 
				  0.28966, -0.37742, -0.38039, 0.29389, 0.4499, 
				  0.18266, -0.37583}, {0.36898, 0.08535, 0.42204, 0.50712, -0.30184, 
				  0.39275, 0.37631, -0.04393, -0.41517, 0.03755, 0.67773, -0.43095, 
				  0.2837, 0.43407, 0.0646, 0.29697, 0.45664, 
				  0.37367, -0.36033, -0.09881, -0.07005, 0.19921, 0.17028, 
				  0.55881, -0.03187, -0.12326}, {0.12316, 0.09257, 
				  0.30596, -0.01982, -0.2436, -0.36395, 0.31257, 0.22589, 0.54184, 
				  0.38368, 0.31873, 0.22516, 0.59916, -0.16742, -0.16148, 0.40872, 
				  0.03349, 0.32731, 0.3726, -0.23023, -0.43623, 0.32939, 0.0377, 
				  0.10851, 0.03702, 0.64725}, {0.44878, 0.4503, -0.35637, 0.12054, 
				  0.47334, -0.42553, -0.1483, -0.16856, -0.31252, 0.14542, 0.31677, 
				  0.39565, -0.46657, 0.53242, 0.27064, 0.31176, 0.14749, -0.04466, 
				  0.21344, 0.05595, 0.05061, -0.44458, 0.47078, -0.19064, -0.00658, 
				  0.13524}, {-0.44776, 0.17071, 0.42999, -0.43261, 0.432, 
				  0.34357, -0.19034, 0.1515, -0.15911, -0.17298, 
				  0.31411, -0.45314, -0.23057, -0.35761, 0.49206, 0.05556, 
				  0.29964, -0.28544, -0.10452, 0.19128, 0.48868, -0.12365, 0.21204, 
				  0.24518, -0.00105, -0.1556}, {0.04033, -0.32159, 0.3333, -0.11618, 
				  0.22933, 0.12657, 0.13472, 0.07732, 0.32989, 0.4106, 0.24549, 
				  0.33384, -0.2387, -0.02597, 0.14001, -0.09841, -0.153, -0.3886, 
				  0.42962, -0.28839, -0.19009, -0.43788, 0.35977, 0.05747, 
				  0.04635, -0.01853}, {0.08815, -0.08093, 0.22009, -0.01599, -0.37681,
				   0.50679, -0.31826, 0.23629, 0.40993, 0.28893, -0.10534, 
				  0.07641, -0.37781, -0.30976, 
				  0.30666, -0.34515, -0.36123, -0.12595, -0.12828, -0.00851, 
				-0.40613, -0.43779, -0.19383, 0.18357, 
				  0.28644, -0.20543}, {0.19766, -0.15491, -0.06888, -0.03281, -0.0911,
				   0.00594, -0.18391, -0.22179, 0.13962, -0.4791, 0.27037, 
				  0.07636, -0.37512, -0.35676, 0.34298, 0.00324, -0.06811, 
				  0.01756, -0.05235, -0.24635, -0.19013, 0.26395, 0.33983, -0.33627, 
				  0.48819, -0.35229}, {0.48512, 0.19158, -0.15878, 0.07377, 0.26878, 
				  0.07683, 0.00713, 0.38699, 0.46326, 0.17898, 0.17598, -0.16669, 
				  0.34255, 0.4337, 0.44676, -0.22437, 0.62995, 0.61611, 
				  0.01819, -0.33776, 0.11844, 0.64214, 0.3183, 0.29469, 
				  0.34149, -0.17138}, {1.41164, 0.73306, 0.62713, 0.99012, 0.20224, 
				  0.64434, -0.00026, 0.69656, 0.45116, 0.59, 0.9594, 0.15043, 0.68028,
				   0.42935, 0.47006, 0.35791, 0.61465, 0.99286, -0.09053, 
				  0.03122, -0.1367, 1.02367, -0.32287, 0.78805, 0.36095, 
				  0.89147}, {-0.07481, -0.16252, -0.40998, -0.15446, 0.44321, 
				  0.19585, -0.42462, -0.47692, -0.44686, -0.43396, -0.17989, 
				-0.31286, -0.11169, 
				  0.43542, -0.19686, -0.02324, -0.44417, -0.19178, -0.20158, 0.29329, 
				  0.29197, -0.34626, -0.25066, -0.00318, 0.22195, 0.4131}, {0.89954, 
				  0.63886, -0.03166, 0.32281, -0.33874, 0.05034, 0.49695, -0.07078, 
				  0.51315, 0.30326, 0.32783, -0.2095, -0.1889, 
				  0.51123, -0.3334, -0.30647, 0.32847, 0.45799, -0.03373, -0.24813, 
				  0.2853, 0.27921, 0.14904, 0.11785, 0.23194, 0.44012}, {-0.05143, 
				  0.33851, 0.17122, 0.13654, 0.47177, 0.31762, -0.24311, 
				  0.16711, -0.0857, -0.01278, 0.36403, -0.1437, 
				  0.20555, -0.25134, -0.25656, 0.06005, -0.23606, 0.25273, 0.21581, 
				  0.30846, -0.3951, -0.25406, -0.20637, -0.18278, -0.54111, 
				  0.37274}, {-0.43516, -0.09475, 0.37042, 0.2701, 0.40906, 
				  0.24266, -0.33035, -0.45187, -0.23618, -0.31302, -0.36248, 
				  0.02317, -0.31236, 0.11072, -0.2628, 0.16882, 
				  0.23567, -0.02041, -0.40868, 0.10994, 0.38161, 0.11768, -0.45622, 
				  0.08254, -0.3211, -0.19202}, {-0.29571, 0.16666, 0.08026, -0.41811, 
				  0.02368, -0.09813, 0.40014, 0.20873, 0.07221, 0.34355, -0.1015, 
				  0.47093, 0.24468, 0.44462, 0.04839, -0.08988, 0.37879, 0.51683, 
				  0.27488, 0.22649, 0.0137, -0.10457, 
				  0.39719, -0.15776, -0.09629, -0.39916}, {0.10067, -0.11682, 0.23676,
				   0.00846, -0.05162, -0.23599, -0.27512, -0.16578, -0.05976, 
				-0.16277, -0.28599, 0.43947, 0.37775, -0.04469, 0.50455, 0.07515, 
				  0.22895, 0.11061, 0.04375, -0.27678, 0.19871, -0.22433, -0.30763, 
				  0.6222, 0.37857, 0.07204}, {0.52589, -0.18635, 0.27313, -0.09339, 
				  0.22082, 0.39887, 0.40718, 0.37804, -0.14013, 0.30243, -0.08426, 
				  0.46421, 0.4514, 0.54191, 0.24013, -0.37428, 0.06914, 0.42134, 
				  0.17548, 0.56828, -0.03514, 0.75668, -0.03988, -0.03461, 0.10419, 
				  0.32648}, {-0.27383, 0.34398, -0.19306, -0.17456, -0.1753, 0.44168, 
				  0.35163, 0.13072, 0.10396, 0.10646, -0.3276, -0.37652, 0.25612, 
				  0.07407, 0.38367, 0.49044, 0.22534, 
				  0.23657, -0.08037, -0.22037, -0.23862, -0.25138, 
				  0.48834, -0.33313, -0.22192, 0.31406}, {1.4702, 0.20009, 0.75108, 
				  0.55715, 0.20304, 0.51657, 0.26406, 0.44805, 0.64681, 0.05463, 
				  0.58731, -0.18502, -0.04684, 0.62745, 0.53421, 0.17806, 0.86766, 
				  1.05699, 0.44207, -0.20877, 0.30988, 1.28093, 0.40973, 1.04652, 
				  0.45202, 0.78737}, {-0.1575, 0.04812, -0.28091, 0.33788, 
				  0.49748, -0.3028, 0.23118, -0.3844, 0.47127, -0.10442, 
				  0.13815, -0.39637, 0.51612, 0.50207, 0.26354, -0.0515, 0.05981, 
				  0.55271, 0.37757, -0.3346, 0.26064, 
				  0.48846, -0.34593, -0.08618, -0.11524, -0.38566}, {0.87514, 0.25942,
				   0.08613, 0.57515, -0.34518, -0.38033, 0.49309, 0.08271, 
				  0.10521, -0.21695, 0.51272, 0.39455, 0.43307, 0.45738, 0.35689, 
				  0.3629, 0.35683, 0.36821, 0.33033, -0.34351, -0.44037, 
				  0.10582, -0.13366, 0.12119, 0.40725, 0.6246}, {0.03121, 
				  0.27448, -0.21911, -0.21781, 0.09966, -0.19187, -0.45196, 0.26018, 
				  0.09646, 0.44229, 0.28466, 0.12399, -0.03951, -0.05672, -0.10581, 
				  0.20563, -0.04795, -0.06552, -0.054, -0.31543, -0.34578, -0.35615, 
				-0.32854, -0.43238, 0.04766, 0.0528}, {0.01576, -0.31438, 0.31641, 
				  0.36065, -0.01779, -0.18388, 0.06257, -0.47231, -0.30796, 
				  0.15767, -0.17549, 0.17127, -0.45541, -0.50537, 0.34067, 0.05355, 
				  0.45556, 0.12376, 
				  0.07581, -0.20127, -0.43871, -0.10464, -0.4812, -0.38342, -0.28979, 
				  0.21049}, {-0.35985, 0.05074, 0.16299, -0.36761, -0.37498, -0.27787,
				   0.18837, -0.34875, 0.47218, 0.24053, -0.09232, -0.32184, 0.5187, 
				  0.1144, -0.42884, 0.22754, -0.13566, 0.40184, -0.45908, -0.22887, 
				  0.2802, 0.11097, -0.36025, 0.42427, -0.24441, -0.41365}, {0.02863, 
				  0.25228, 0.44842, 0.52481, 0.47908, 0.43123, -0.25819, 0.37141, 
				  0.41895, 0.02685, -0.19316, 0.39867, -0.03228, 0.46221, 0.57762, 
				  0.17811, -0.23151, -0.39044, 0.43746, 0.53341, 0.48958, 
				  0.48232, -0.12506, -0.02175, -0.2764, -0.15422}}
	);
	
	public static DenseMatrix OUT_5 = new DenseMatrix(new double[][] 
			{{-0.37001, -0.73681, -0.55293, -0.35443, -0.0665, 0.03667, -0.14775, 
				  0.05112, -0.71163, -2.09164, 0.26133, -0.80937, -0.1645, 
				  0.26235, -0.23943, -0.51803, -0.6868, -0.0361, -1.96348, -0.43248, 
				-0.92604, 0.28027, 0.14547, -0.23563, -0.59338, -0.43828}}
	);
}
