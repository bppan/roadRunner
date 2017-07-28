package lib.svm;
import libsvm.*;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class svm_scale
{
	private String line = null;
	private double lower = -1.0;
	private double upper = 1.0;
	private double y_lower;
	private double y_upper;
	private boolean y_scaling = false;
	private double[] feature_max;
	private double[] feature_min;
	private double y_max = -Double.MAX_VALUE;
	private double y_min = Double.MAX_VALUE;
	private int max_index;
	private long num_nonzeros = 0;
	private long new_num_nonzeros = 0;
	private BufferedReader fp_restore_change = null;
	private String restore_filename_change; 
	private static void exit_with_help()
	{
		System.out.print(
		 "Usage: svm-scale [options] data_filename\n"
		+"options:\n"
		+"-l lower : x scaling lower limit (default -1)\n"
		+"-u upper : x scaling upper limit (default +1)\n"
		+"-y y_lower y_upper : y scaling limits (default: no y scaling)\n"
		+"-s save_filename : save scaling parameters to save_filename\n"
		+"-r restore_filename : restore scaling parameters from restore_filename\n"
		);
		System.exit(1);
	}

	private BufferedReader rewind(BufferedReader fp, String filename) throws IOException
	{
		fp.close();
		return new BufferedReader(new FileReader(filename));
	}

	private void output_target(double value)
	{
		if(y_scaling)
		{
			if(value == y_min)
				value = y_lower;
			else if(value == y_max)
				value = y_upper;
			else
				value = y_lower + (y_upper-y_lower) *
				(value-y_min) / (y_max-y_min);
		}

		System.out.print(value + " ");
	}
	//新增
	private void output_target(double value, BufferedWriter pw)
	{
		if(y_scaling)
		{
			if(value == y_min)
				value = y_lower;
			else if(value == y_max)
				value = y_upper;
			else
				value = y_lower + (y_upper-y_lower) *
				(value-y_min) / (y_max-y_min);
		}

		System.out.print(value + " ");
		try {
			pw.write(value + " ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String output_target_string(double value)
	{
		if(y_scaling)
		{
			if(value == y_min)
				value = y_lower;
			else if(value == y_max)
				value = y_upper;
			else
				value = y_lower + (y_upper-y_lower) *
				(value-y_min) / (y_max-y_min);
		}

		return value + " ";
	}
	private void output(int index, double value)
	{
		/* skip single-valued attribute */
		if(feature_max[index] == feature_min[index])
			return;

		if(value == feature_min[index])
			value = lower;
		else if(value == feature_max[index])
			value = upper;
		else
			value = lower + (upper-lower) * 
				(value-feature_min[index])/
				(feature_max[index]-feature_min[index]);

		if(value != 0)
		{
			System.out.print(index + ":" + value + " ");
			new_num_nonzeros++;
		}
	}
	//新增
	private void output(int index, double value,BufferedWriter pw)
	{
		/* skip single-valued attribute */
		if(feature_max[index] == feature_min[index])
			return;

		if(value == feature_min[index])
			value = lower;
		else if(value == feature_max[index])
			value = upper;
		else
			value = lower + (upper-lower) * 
				(value-feature_min[index])/
				(feature_max[index]-feature_min[index]);

		if(value != 0)
		{
			System.out.print(index + ":" + value + " ");
			try {
				pw.write(index + ":" + value + " ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new_num_nonzeros++;
		}
	}
	private String output_string(int index, double value)
	{
		/* skip single-valued attribute */
		if(feature_max[index] == feature_min[index])
			return"";

		if(value == feature_min[index])
			value = lower;
		else if(value == feature_max[index])
			value = upper;
		else
			value = lower + (upper-lower) * 
				(value-feature_min[index])/
				(feature_max[index]-feature_min[index]);

		if(value != 0)
		{
			return index + ":" + value + " ";
		}else{
			return"";
		}
	}
	private String readline(BufferedReader fp) throws IOException
	{
		line = fp.readLine();
		return line;
	}
	public void setScaleParametes(String scale_paramatesr) throws IOException{
		this.restore_filename_change = scale_paramatesr;
		this.fp_restore_change = null;
		if(	this.restore_filename_change  != null)
		{
			int idx, c;

			try {
				this.fp_restore_change = new BufferedReader(new FileReader(	this.restore_filename_change ));
			}
			catch (Exception e) {
				System.err.println("can't open file " + this.restore_filename_change );
				System.exit(1);
			}
			if((c = this.fp_restore_change.read()) == 'y')
			{
				this.fp_restore_change.readLine();
				this.fp_restore_change.readLine();		
				this.fp_restore_change.readLine();		
			}
			this.fp_restore_change.readLine();
			this.fp_restore_change.readLine();

			String restore_line = null;
			while((restore_line = this.fp_restore_change.readLine())!=null)
			{
				StringTokenizer st2 = new StringTokenizer(restore_line);
				idx = Integer.parseInt(st2.nextToken());
				max_index = Math.max(max_index, idx);
			}
			this.fp_restore_change = rewind(this.fp_restore_change, this.restore_filename_change );
		}
	}
	public String run_scale(String Scale) throws IOException{
		int index;
		this.line = Scale;
		if(!Scale.isEmpty())
		{
			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
			st.nextToken();
			while(st.hasMoreTokens())
			{
				index = Integer.parseInt(st.nextToken());
				max_index = Math.max(max_index, index);
				st.nextToken();
				num_nonzeros++;
			}
		}
		try {
			feature_max = new double[(max_index+1)];
			feature_min = new double[(max_index+1)];
		} catch(OutOfMemoryError e) {
			System.err.println("can't allocate enough memory");
			System.exit(1);
		}

		for(int i=0;i<=max_index;i++)
		{
			feature_max[i] = -Double.MAX_VALUE;
			feature_min[i] = Double.MAX_VALUE;
		}
		if(!Scale.isEmpty())
		{
			int next_index = 1;
			double target;
			double value;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
			target = Double.parseDouble(st.nextToken());
			y_max = Math.max(y_max, target);
			y_min = Math.min(y_min, target);

			while (st.hasMoreTokens())
			{
				index = Integer.parseInt(st.nextToken());
				value = Double.parseDouble(st.nextToken());

				for (int i = next_index; i<index; i++)
				{
					feature_max[i] = Math.max(feature_max[i], 0);
					feature_min[i] = Math.min(feature_min[i], 0);
				}

				feature_max[index] = Math.max(feature_max[index], value);
				feature_min[index] = Math.min(feature_min[index], value);
				next_index = index + 1;
			}

			for(int i=next_index;i<=max_index;i++)
			{
				feature_max[i] = Math.max(feature_max[i], 0);
				feature_min[i] = Math.min(feature_min[i], 0);
			}
		}
		int idx, c;
		double fmin, fmax;
		this.fp_restore_change = rewind(this.fp_restore_change, this.restore_filename_change);
		this.fp_restore_change.mark(2);				// for reset
		if((c = this.fp_restore_change.read()) == 'y')
		{
			this.fp_restore_change.readLine();		// pass the '\n' after 'y'
			StringTokenizer st = new StringTokenizer(this.fp_restore_change.readLine());
			y_lower = Double.parseDouble(st.nextToken());
			y_upper = Double.parseDouble(st.nextToken());
			st = new StringTokenizer(this.fp_restore_change.readLine());
			y_min = Double.parseDouble(st.nextToken());
			y_max = Double.parseDouble(st.nextToken());
			y_scaling = true;
		}
		else
			this.fp_restore_change.reset();

		if(this.fp_restore_change.read() == 'x') {
			this.fp_restore_change.readLine();		// pass the '\n' after 'x'
			StringTokenizer st = new StringTokenizer(this.fp_restore_change.readLine());
			lower = Double.parseDouble(st.nextToken());
			upper = Double.parseDouble(st.nextToken());
			String restore_line = null;
			while((restore_line = this.fp_restore_change.readLine())!=null)
			{
				StringTokenizer st2 = new StringTokenizer(restore_line);
				idx = Integer.parseInt(st2.nextToken());
				fmin = Double.parseDouble(st2.nextToken());
				fmax = Double.parseDouble(st2.nextToken());
				if (idx <= max_index)
				{
					feature_min[idx] = fmin;
					feature_max[idx] = fmax;
				}
			}
		}
		this.fp_restore_change.close();
		
		String scale_result = "";
		if(!Scale.isEmpty())
		{
			int next_index = 1;
			double target;
			double value;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
			target = Double.parseDouble(st.nextToken());
			scale_result += output_target_string(target);
			while(st.hasMoreElements())
			{
				index = Integer.parseInt(st.nextToken());
				value = Double.parseDouble(st.nextToken());
				for (int i = next_index; i<index; i++){
					scale_result += output_string(i, 0);
				}
				scale_result += output_string(index, value);
				next_index = index + 1;
			}

			for(int i=next_index;i<= max_index;i++){
				scale_result += output_string(i, 0);
			}
			return scale_result;
		}
		return "";
	}
	private void run(String []argv) throws IOException
	{
		int i,index;
		BufferedReader fp = null, fp_restore = null;
		String save_filename = null;
		String restore_filename = null;
		String data_filename = null;
		String output_filename = null;

		for(i=0;i<argv.length;i++)
		{
			if (argv[i].charAt(0) != '-')	break;
			++i;
			switch(argv[i-1].charAt(1))
			{
				case 'l': lower = Double.parseDouble(argv[i]);	break;
				case 'u': upper = Double.parseDouble(argv[i]);	break;
				case 'y':
					  y_lower = Double.parseDouble(argv[i]);
					  ++i;
					  y_upper = Double.parseDouble(argv[i]);
					  y_scaling = true;
					  break;
				case 's': save_filename = argv[i];	break;
				case 'r': restore_filename = argv[i];	break;
				case 'o':	output_filename = argv[i];	break;
				default:
					  System.err.println("unknown option");
					  exit_with_help();
			}
		}

		if(!(upper > lower) || (y_scaling && !(y_upper > y_lower)))
		{
			System.err.println("inconsistent lower/upper specification");
			System.exit(1);
		}
		if(restore_filename != null && save_filename != null)
		{
			System.err.println("cannot use -r and -s simultaneously");
			System.exit(1);
		}

		if(argv.length != i+1)
			exit_with_help();

		data_filename = argv[i];
		try {
			fp = new BufferedReader(new FileReader(data_filename));
		} catch (Exception e) {
			System.err.println("can't open file " + data_filename);
			System.exit(1);
		}

		/* assumption: min index of attributes is 1 */
		/* pass 1: find out max index of attributes */
		max_index = 0;

		if(restore_filename != null)
		{
			int idx, c;

			try {
				fp_restore = new BufferedReader(new FileReader(restore_filename));
			}
			catch (Exception e) {
				System.err.println("can't open file " + restore_filename);
				System.exit(1);
			}
			if((c = fp_restore.read()) == 'y')
			{
				fp_restore.readLine();
				fp_restore.readLine();		
				fp_restore.readLine();		
			}
			fp_restore.readLine();
			fp_restore.readLine();

			String restore_line = null;
			while((restore_line = fp_restore.readLine())!=null)
			{
				StringTokenizer st2 = new StringTokenizer(restore_line);
				idx = Integer.parseInt(st2.nextToken());
				max_index = Math.max(max_index, idx);
			}
			fp_restore = rewind(fp_restore, restore_filename);
		}

		while (readline(fp) != null)
		{
			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
			st.nextToken();
			while(st.hasMoreTokens())
			{
				index = Integer.parseInt(st.nextToken());
				max_index = Math.max(max_index, index);
				st.nextToken();
				num_nonzeros++;
			}
		}

		try {
			feature_max = new double[(max_index+1)];
			feature_min = new double[(max_index+1)];
		} catch(OutOfMemoryError e) {
			System.err.println("can't allocate enough memory");
			System.exit(1);
		}

		for(i=0;i<=max_index;i++)
		{
			feature_max[i] = -Double.MAX_VALUE;
			feature_min[i] = Double.MAX_VALUE;
		}

		fp = rewind(fp, data_filename);

		/* pass 2: find out min/max value */
		while(readline(fp) != null)
		{
			int next_index = 1;
			double target;
			double value;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
			target = Double.parseDouble(st.nextToken());
			y_max = Math.max(y_max, target);
			y_min = Math.min(y_min, target);

			while (st.hasMoreTokens())
			{
				index = Integer.parseInt(st.nextToken());
				value = Double.parseDouble(st.nextToken());

				for (i = next_index; i<index; i++)
				{
					feature_max[i] = Math.max(feature_max[i], 0);
					feature_min[i] = Math.min(feature_min[i], 0);
				}

				feature_max[index] = Math.max(feature_max[index], value);
				feature_min[index] = Math.min(feature_min[index], value);
				next_index = index + 1;
			}

			for(i=next_index;i<=max_index;i++)
			{
				feature_max[i] = Math.max(feature_max[i], 0);
				feature_min[i] = Math.min(feature_min[i], 0);
			}
		}

		fp = rewind(fp, data_filename);

		/* pass 2.5: save/restore feature_min/feature_max */
		if(restore_filename != null)
		{
			// fp_restore rewinded in finding max_index 
			int idx, c;
			double fmin, fmax;

			fp_restore.mark(2);				// for reset
			if((c = fp_restore.read()) == 'y')
			{
				fp_restore.readLine();		// pass the '\n' after 'y'
				StringTokenizer st = new StringTokenizer(fp_restore.readLine());
				y_lower = Double.parseDouble(st.nextToken());
				y_upper = Double.parseDouble(st.nextToken());
				st = new StringTokenizer(fp_restore.readLine());
				y_min = Double.parseDouble(st.nextToken());
				y_max = Double.parseDouble(st.nextToken());
				y_scaling = true;
			}
			else
				fp_restore.reset();

			if(fp_restore.read() == 'x') {
				fp_restore.readLine();		// pass the '\n' after 'x'
				StringTokenizer st = new StringTokenizer(fp_restore.readLine());
				lower = Double.parseDouble(st.nextToken());
				upper = Double.parseDouble(st.nextToken());
				String restore_line = null;
				while((restore_line = fp_restore.readLine())!=null)
				{
					StringTokenizer st2 = new StringTokenizer(restore_line);
					idx = Integer.parseInt(st2.nextToken());
					fmin = Double.parseDouble(st2.nextToken());
					fmax = Double.parseDouble(st2.nextToken());
					if (idx <= max_index)
					{
						feature_min[idx] = fmin;
						feature_max[idx] = fmax;
					}
				}
			}
			fp_restore.close();
		}

		if(save_filename != null)
		{
			Formatter formatter = new Formatter(new StringBuilder());
			BufferedWriter fp_save = null;

			try {
				fp_save = new BufferedWriter(new FileWriter(save_filename));
			} catch(IOException e) {
				System.err.println("can't open file " + save_filename);
				System.exit(1);
			}

			if(y_scaling)
			{
				formatter.format("y\n");
				formatter.format("%.16g %.16g\n", y_lower, y_upper);
				formatter.format("%.16g %.16g\n", y_min, y_max);
			}
			formatter.format("x\n");
			formatter.format("%.16g %.16g\n", lower, upper);
			for(i=1;i<=max_index;i++)
			{
				if(feature_min[i] != feature_max[i]) 
					formatter.format("%d %.16g %.16g\n", i, feature_min[i], feature_max[i]);
			}
			fp_save.write(formatter.toString());
			fp_save.close();
		}

		/* pass 3: scale */
		BufferedWriter pw = null;
		
		try {
			pw = new BufferedWriter(new FileWriter(output_filename));
		} catch(IOException e) {
			System.err.println("can't open file " + output_filename);
			System.exit(1);
		}
		
		
		
		while(readline(fp) != null)
		{
			int next_index = 1;
			double target;
			double value;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
			target = Double.parseDouble(st.nextToken());
			output_target(target);
			output_target(target, pw);
			while(st.hasMoreElements())
			{
				index = Integer.parseInt(st.nextToken());
				value = Double.parseDouble(st.nextToken());
				for (i = next_index; i<index; i++){
					output(i, 0);
					output(i, 0, pw);
				}
				output(index, value);
				output(index, value,pw);
				next_index = index + 1;
			}

			for(i=next_index;i<= max_index;i++){
				output(i, 0);
				output(i, 0, pw);
			}
			System.out.print("\n");
			pw.write("\n");
		}
		pw.close();
		
		if (new_num_nonzeros > num_nonzeros)
			System.err.print(
			 "WARNING: original #nonzeros " + num_nonzeros+"\n"
			+"         new      #nonzeros " + new_num_nonzeros+"\n"
			+"Use -l 0 if many original feature values are zeros\n");

		fp.close();
	}

	public static void main(String argv[]) throws IOException
	{
		svm_scale s = new svm_scale();
		s.run(argv);
	}
	
}
